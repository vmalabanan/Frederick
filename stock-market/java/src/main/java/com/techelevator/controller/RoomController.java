package com.techelevator.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.techelevator.dao.StockDao;
import com.techelevator.dao.TradeDao;
import com.techelevator.dao.UserDao;
import com.techelevator.dao.ApiStockDao.StockApi;
import com.techelevator.model.InviteSIMP;
import com.techelevator.model.LobbySIMP;
import com.techelevator.model.PortfolioDTO;
import com.techelevator.model.Stock;

@Controller
public class RoomController {

	private static final Logger log = LoggerFactory.getLogger(RoomController.class);
	private final SimpMessagingTemplate simpMessagingTemplate;

	private static Map<String, List<String>> rooms = new HashMap<>();

	private TradeDao tradeDao;
	private StockDao stockDao;

	@Autowired
	public RoomController(StockDao stockDao, TradeDao tradeDao, UserDao userDao,
			SimpMessagingTemplate simpMessagingTemplate) {
		this.simpMessagingTemplate = simpMessagingTemplate;
		this.tradeDao = tradeDao;
		this.stockDao = stockDao;
	}

	// Demo Section

	public StockApi GRGO = new StockApi("GRGO", "Gregor Coin",
			BigDecimal.valueOf(2.4), BigDecimal.valueOf(0.2));
	public StockApi NNJA = new StockApi("NNJA", "Ninja Coin",
			BigDecimal.valueOf(10.0), BigDecimal.valueOf(0.2));
	private boolean grgo = false;
	private boolean nnja = false;
	private boolean variance = false;

	@MessageMapping("/crash")
	public String crashStock(boolean state) {
		nnja = state;
		return state ? "Crashing Ninja Stock" : "Stopped Crashing Ninja Stock";
	}

	@MessageMapping("/pump")
	public String pumpStock(boolean state) {
		grgo = state;
		return state ? "Pumping GRGO Stock" : "Stopped Pumping GRGO Stock";
	}

	@MessageMapping("/variant")
	public String variantStock(boolean state) {
		variance = state;
		return state ? "started variance" : "stopped variance";

	}

	// Demo Section

	@MessageMapping("/room-{gameId}/join")
	public LobbySIMP joinRoom(@DestinationVariable String gameId, String username) {
		log.debug("[Room {} JOIN]: {}", gameId, username);
		if (!rooms.containsKey(gameId)) {
			rooms.put(gameId, new ArrayList<String>());
		}
		List<String> players = rooms.get(gameId);
		if (players.contains(username)) {
			log.debug("[Room {}] {} already exist", gameId, username);
			return new LobbySIMP(gameId, players);
		}
		players.add(username);
		rooms.put(gameId, players);
		log.debug("[Room {}]: {} players ", gameId, players);
		return new LobbySIMP(gameId, players);
	}

	@MessageMapping("/invite")
	public String inviteUser(String username) {
		log.debug("Sent invite to {}", username);
		return username;
	}

	@MessageMapping("room-{gameId}/leave")
	public LobbySIMP leaveRoom(@DestinationVariable String gameId, String username) {
		log.debug("[Room {} LEAVE]: {} Left", gameId, username);
		List<String> players = rooms.get(gameId);
		players.remove(username);
		rooms.put(gameId, players);
		log.debug("[Room {}]: {} players ", gameId, players);
		return new LobbySIMP(gameId, players);
	}

	private List<String> getOwnedSymbols(String gameId) {
		List<String> allSymbols = new ArrayList<>();
		Integer id = Integer.parseInt(gameId);
		List<PortfolioDTO> portfolios = tradeDao.getCurrentPortfolioAllPlayers(id);
		for (PortfolioDTO portfolio : portfolios) {
			portfolio.getPortfolio().getStocks().stream().forEach(s -> {
				String symbol = s.getTickerSymbol();
				if (!allSymbols.contains(symbol)) {
					allSymbols.add(symbol);
				}
			});
		}
		return allSymbols;
	}

	// demo function
	private StockApi applyVariance(StockApi stock) {
		if (Math.random() > 0.5) {
			stock.setPrice(stock.getPrice().subtract(new BigDecimal(0.1)));
			stock.setChangesPercentage(stock.getChangesPercentage().subtract(new BigDecimal(Math.random())));
		} else {
			stock.setPrice(stock.getPrice().add(new BigDecimal(0.1)));
			stock.setChangesPercentage(stock.getChangesPercentage().add(new BigDecimal(Math.random())));
		}
		return stock;
	}

	// demo function

	@Scheduled(fixedRate = 1000)
	public void stockUpdatePerSec() {
		List<String> allSymbols = new ArrayList<>();
		for (String gameId : rooms.keySet()) {
			if (rooms.get(gameId).size() == 0) {
				continue;
			}
			allSymbols = Stream.concat(allSymbols.stream(), getOwnedSymbols(gameId).stream())
					.collect(Collectors.toList());
		}

		allSymbols = allSymbols.stream().distinct().collect(Collectors.toList());
		List<StockApi> data = new ArrayList<>(stockDao.getQuote(String.join(",", allSymbols)));
		// demo data

		if (grgo) {
			GRGO.setPrice(GRGO.getPrice().add(new BigDecimal(Math.random())));
			GRGO.setChangesPercentage(GRGO.getChangesPercentage().add(new BigDecimal(0.1)));

		}
		if (nnja) {
			NNJA.setPrice(NNJA.getPrice().subtract(new BigDecimal(Math.random())));
			NNJA.setChangesPercentage(NNJA.getChangesPercentage().subtract(new BigDecimal(0.1)));
		}

		data.add(GRGO);
		data.add(NNJA);

		if (variance) {
			data = data.stream().map(s -> applyVariance(s)).collect(Collectors.toList());
		}
		// demo data

		log.debug("Sending Data");
		simpMessagingTemplate.convertAndSend("/topic/update", data);

		List<Leaderboard> leaderboards = new ArrayList<>();
		for (String gameId : rooms.keySet()) {
			if (rooms.get(gameId).size() == 0) {
				continue;
			}
			Leaderboard leaderboard = new Leaderboard();
			Integer id = Integer.parseInt(gameId);
			List<PortfolioDTO> portfolios = tradeDao.getCurrentPortfolioAllPlayers(id);
			leaderboard.setGameId(id);

			for (PortfolioDTO portfolio : portfolios) {
				BigDecimal currentCash = portfolio.getPortfolio().getCash();
				BigDecimal sumOfStocks = BigDecimal.ZERO;
				for (Stock currentStock : portfolio.getPortfolio().getStocks()) {
					Optional<StockApi> stockData = data.stream()
							.filter(s -> s.getSymbol().equals(currentStock.getTickerSymbol())).findFirst();
					BigDecimal totalPricePerStockOwned = stockData.get().getPrice()
							.multiply(new BigDecimal(currentStock.getNumberOfShares()));
					sumOfStocks = sumOfStocks.add(totalPricePerStockOwned);
				}
				leaderboard.addPlayers(portfolio.getUsername(), sumOfStocks.add(currentCash));
			}
			leaderboards.add(leaderboard);
		}
		log.debug("Sending leaderboards", leaderboards);
		simpMessagingTemplate.convertAndSend("/topic/leaderboard", leaderboards);
	}

	public static class Leaderboard {
		private int gameId;
		private Map<String, BigDecimal> players = new HashMap<>();

		public int getGameId() {
			return this.gameId;
		}

		public void setGameId(int gameId) {
			this.gameId = gameId;
		}

		public Map<String, BigDecimal> getPlayers() {
			return this.players;
		}

		public void setPlayers(Map<String, BigDecimal> players) {
			this.players = players;
		}

		public void addPlayers(String username, BigDecimal accountValue) {
			players.put(username, accountValue);
		}

	}
}
