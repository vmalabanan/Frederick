-- The following line terminates any active connections to the database so that it can be dropped
SELECT pg_terminate_backend(pid) 
FROM pg_stat_activity 
WHERE datname = 'stock_market';

DROP DATABASE IF EXISTS stock_market;

DROP USER IF EXISTS stock_market_owner;
DROP USER IF EXISTS stock_market_appuser;

-- then create the database
CREATE DATABASE stock_market;
