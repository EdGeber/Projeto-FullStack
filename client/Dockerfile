FROM node:22-alpine3.19
# servidor http simples para servir conteúdo estatico
RUN npm install -g http-server
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build-only
EXPOSE 8080
CMD [ "http-server", "dist" ]
