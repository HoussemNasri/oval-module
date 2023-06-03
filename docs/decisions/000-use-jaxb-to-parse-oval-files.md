---
nav_order: 0
parent: Decision Records
status: proposed
date: 2023-06-01
---
# Use JAXB to parse OVAL files
## Context and Problem Statement
We need an XML parsing library that can parse OVAL files. Particularly, it should be capable of parsing large XML files efficiently, and have tools to auto-generate java classes from XML schemas.
## Considered Options
* Jackson
* JAXB
* StAX
## Decision Outcome
Chosen option: **JAXB**, because it provides the `xjc` tool to auto-generate JAXB-compatible java classes from XML schemas, and is a battle-tested java standard API.
## Pros and Cons of the Options
### Jackson
* Good, because Uyuni already depends on it
* Bad, because it doesn't provide a tool to auto-generate java classes from xsd schema
* Bad, because it was designed primarily to parse JSON *(tradeoffs over XML support might have been made by the developers)*
### JAXB
* Good, because Uyuni already depends on it
* Good, because it is a standard java API
* Good, because it provides a tool `xjc` to auto-generate java classes from xsd schema
* Bad, because it consumes a lot of memory
### StAX
* Good, because Uyuni already depends on it
* Good, because it is a standard java API
* Good, because it consumes less memory
* Bad, because it doesn't map XML elements to java objects automatically