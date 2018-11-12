# bridges

A Kurosawa library for interacting with Key-Value stores.

![Jeff Bridges](http://i.imgur.com/6pekpsV.jpg)


## What does it do?

Provides the protocols for three levels of API access to Key-Value stores:

StringSetStore - an API for interacting with sets of keys.

CheckAndSetStore - a low-level API for creating, reading and writing values using check-and-set (CAS) semantics.

KeyValueStore - a simplified high-level API for creating, reading and writing values.


## Generate Docs

    $ lein docs
    $ open doc/dist/latest/api/index.html
    
## Deploying 

First, setup your GPG credentials and Leiningen environment.

See these for details:

https://github.com/technomancy/leiningen/blob/master/doc/GPG.md
https://github.com/technomancy/leiningen/blob/master/doc/DEPLOY.md#authentication

### TL;DR

To release a snapshot:

    $ lein release :patch

To release a minor version:

    $ lein release :minor
    
To release a major version:

    $ lein release :major
