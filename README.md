# Week 1 – Hash Table Based System Design Implementations

## Overview

This repository contains **10 Java implementations of real-world system design problems** that heavily rely on **Hash Tables and related data structures**.
Each problem simulates a **large-scale production system** such as caching systems, analytics pipelines, rate limiters, and fraud detection tools.

The goal of this repository is to demonstrate how **hash-based data structures enable high-performance systems** with **O(1) average time complexity for lookups, inserts, and updates**.

---

## Technologies Used

* **Language:** Java
* **IDE:** IntelliJ IDEA
* **Core Data Structures:**

    * HashMap
    * LinkedHashMap
    * HashSet
    * PriorityQueue
    * Trie
    * Arrays with Open Addressing

---

## Implemented Systems

### 1. Social Media Username Availability Checker

* O(1) username lookup using HashMap
* Tracks username attempt frequency
* Suggests alternative usernames

### 2. E-commerce Flash Sale Inventory Manager

* Real-time inventory tracking during flash sales
* Thread-safe purchase handling
* Waiting list for out-of-stock items

### 3. DNS Cache with TTL

* Domain → IP caching with expiration
* Automatic invalidation using TTL
* Cache hit/miss statistics

### 4. Plagiarism Detection System

* Uses **n-gram hashing**
* Detects document similarity
* Calculates plagiarism percentage

### 5. Real-Time Website Analytics Dashboard

* Processes streaming page-view events
* Tracks top pages and unique visitors
* Calculates traffic source distribution

### 6. Distributed API Rate Limiter

* Token bucket algorithm
* Per-client request tracking
* Prevents API abuse

### 7. Search Engine Autocomplete System

* Trie + HashMap hybrid
* Suggests top search queries by frequency
* Supports prefix-based search

### 8. Smart Parking Lot Management

* Uses **Open Addressing with Linear Probing**
* Handles collision resolution
* Tracks parking time and billing

### 9. Financial Transaction Analysis (Two-Sum Variants)

* Fraud detection using hash-based lookup
* Time-window transaction matching
* Duplicate payment detection

### 10. Multi-Level Cache System

* L1 (Memory) → L2 (SSD) → L3 (Database)
* LRU eviction policy
* Cache promotion based on access frequency

---

## Key Concepts Demonstrated

* Hash Table Design and Optimization
* Collision Resolution Techniques
* Frequency Counting Applications
* Time-Based Caching Mechanisms
* Open Addressing and Linear Probing
* Trie Structures for Fast Prefix Search
* Multi-Level Cache Architectures
* Streaming Data Processing
* System Scalability Considerations

---

## Learning Outcomes

Through these implementations, the following system design principles are explored:

* Designing **high-performance lookup systems**
* Managing **large-scale concurrent workloads**
* Implementing **efficient caching layers**
* Applying **hashing techniques in real-world applications**
* Understanding **trade-offs between time and space complexity**

---


