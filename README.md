## Environment Setup

1. Copy `.env.example` to `.env`:
   ```bash
   cp .env.example .env
   ```

2. Update the `.env` file with your local configuration:
   - Database credentials
   - CORS settings
   - Server port

3. For production deployment, set these environment variables in Heroku:
   ```bash
   heroku config:set DB_USERNAME=prod_username
   heroku config:set DB_PASSWORD=prod_password
   heroku config:set CORS_ALLOWED_ORIGINS=https://your-frontend-domain.com
   ``` 