# bybit-clj

A Clojure wrapper for the [Bybit API](https://bybit-exchange.github.io/docs/v5/intro), rest and websocket endpoints are available. Authentication and fastest json library is built in.

## Docs

`lein codox` to generate docs in html format, is saved to docs/ folder in the root directory of this project.

## Installation

Add the dependency to your project or build file.

[![Clojars Project](https://img.shields.io/clojars/v/org.clojars.sven_11/bybit-clj)](https://clojars.org/org.clojars.sven_11/bybit-clj)

## Usage

## Quick Start

First, require it in the REPL:

```clojure
(require '[bybit-clj.market :as bb-market])
```

Or in your application:

```clojure
(ns my-app.core
  (:require [bybit-clj.ws :as bb-ws]))
```

The library is constructed similar to the [Bybit API](https://bybit-exchange.github.io/docs/v5/intro), for example, if you need to call a function that is under the `Market` tab. You will find that under `bybit-clj.market`.

### Endpoints

Each function takes in a client, which requires a `:url` and optional authentication values (for private endpoints). 

```clojure
(...
(:require [bybit-clj.trade :as trade]
          [bybit-clj.market :as market]))

(def client {:url trade/url-trade-testnet
                  :key "key"
                  :secret "secret"})

(trade/place-order client "spot" "BTCUSDT" "Buy" "Limit" "0.001" {:price "30000"})

(trade/cancel-all-orders client "spot" {})

(trade/get-open-orders client "spot" {})

(market/get-time {:url market/url-market})
```


### Websocket Feed

Heartbeat is currently not handled by choice, send a [heartbeat packet](https://bybit-exchange.github.io/docs/v5/ws/connect#how-to-send-the-heartbeat-packet) every ~20 seconds to ensure connection.

`opts` is a map that takes the following keys:

- `:url` - the websocket URL
- `:channels` -  vector of subscription strings eg. `["orderbook.50.BTCUSDT"]`
- `:key` - optional - your Bybit API key
- `:secret` - optional - your Bybit API secret
- `:on-connect` - optional - A unary function called after the connection has been established. The argument is a [WebSocketSession](https://eclipse.dev/jetty/javadoc/jetty-12/org/eclipse/jetty/websocket/common/WebSocketSession.html).
- `:on-error` - optional - A unary function called in case of errors. The argument is a `Throwable` describing the error.
- `:on-close` - optional - A binary function called when the connection is closed. Arguments are an `int` status code and a `String` description of reason.    

```clojure
(def conn (create-websocket-connection 
   {:url "wss://stream.bybit.com/v5/public/spot"
    :channels ["orderbook.50.BTCUSDT" "publicTrade.BTCUSDT"]
    :on-receive (fn [x] (prn x))}))

(def conn (create-websocket-connection 
   {:url "wss://stream.bybit.com/v5/private"
    :key "invaluable-secret-key"
    :secret "my-super-secret-secret"
    :channels ["execution"]
    :on-receive (fn [x] (prn x))}))
```
