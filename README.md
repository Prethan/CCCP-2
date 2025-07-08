# SYOS Billing & Stock Management System

This Java-based application was developed as part of a course project for CCCP. It simulates the billing and stock management system of a fictional grocery store named **Synex Outlet Store (SYOS)** located in Colombo.

---

## 🛒 Project Overview

SYOS faced long queues and billing inefficiencies due to a manual system. This project automates the store's billing and inventory processes to:

- Speed up customer checkout
- Reduce manual errors
- Manage stock levels and reorder points efficiently
- Generate daily operational reports

---

## 🔧 Key Features

### ✅ Part 1 – Console Application
- Item purchase entry by item code
- Bill generation with:
  - Item name, quantity, total per item
  - Discounts, total price, cash tendered, and change
- Automatic stock updates post-sale
- Inventory managed by batch and expiry date
- Reports:
  - Total daily sales
  - End-of-day reshelving
  - Low-stock/reorder alerts
  - Current batch-wise stock
  - All bills issued

### 🖥️ Part 2 – GUI & Client-Server Upgrade
- Replaced CLI with a **Graphical User Interface (GUI)**
- Refactored using **Clean Architecture**
- Introduced **Concurrency** and **multi-tier client-server** architecture
- Added **automated test clients** to simulate multiple asynchronous user interactions

---

## 🛠️ Tech Stack

- Java (Core + OOP)
- JavaFX (for GUI)
- Sockets/Threads (for client-server concurrency)
- JUnit (for test automation)

---

## 🚀 How to Run

### 1. Clone the repo

```bash
git clone (https://github.com/Prethan/CCCP-2.git)
