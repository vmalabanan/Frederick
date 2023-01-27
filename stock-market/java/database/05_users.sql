-- ********************************************************************************
-- This script creates the database users and grants them the necessary permissions
-- ********************************************************************************

CREATE USER stock_market_owner
WITH PASSWORD 'stock_market_pass';

GRANT ALL
ON ALL TABLES IN SCHEMA public
TO stock_market_owner;

GRANT ALL
ON ALL SEQUENCES IN SCHEMA public
TO stock_market_owner;

CREATE USER stock_market_appuser
WITH PASSWORD 'stock_market_pass';

GRANT SELECT, INSERT, UPDATE, DELETE
ON ALL TABLES IN SCHEMA public
TO stock_market_appuser;

GRANT USAGE, SELECT
ON ALL SEQUENCES IN SCHEMA public
TO stock_market_appuser;
