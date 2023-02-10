import SockJS from "sockjs-client";
import Stomp from "webstomp-client";

const BASE_SOCKET = "http://34.102.118.144:8080/"
// const BASE_SOCKET = "http://localhost:8080/"
// const SUB_POINT = "topic/"
// const SEND_POINT = "app/"

export default {
    startConnection() {
        const sockJs = new SockJS(BASE_SOCKET);
        const stompClient = Stomp.over(sockJs);
        stompClient.debug = () => { return; }
        return stompClient;
    },
}