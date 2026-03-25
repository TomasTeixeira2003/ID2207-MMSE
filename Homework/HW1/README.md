# Homework 1: Software Engineering Methods

This repository contains the solutions for **Homework 1** of the **Modern Methods of Software Engineering** course (September 2024). The project focuses on translating complex business requirements into standard UML (Unified Modeling Language) diagrams to visualize system behavior and structure.

## Project Overview

The homework explores two distinct case studies requiring different modeling approaches to automate organizational workflows.

### Case Study 1: Salary Raise Management System
An IT company requires a system to automate the communication between developers, IT managers, and financial managers regarding salary increases.
* **Actors**: Software Developer, IT Manager, Financial Manager.
* **Workflow**: Involves performance report checks, priority evaluation, and budget assessment.
* **Deliverables**: 
    * **Use Case Diagram**: High-level visualization of system functionalities and actor interactions.
    * **Use Case Description**: Detailed textual specification of the "Manage Salary Raise Request" process.

### Case Study 2: Hospital Resource Ordering
A hospital system for requesting and ordering medical materials between branches and external suppliers.
* **Scenarios**: Local store requests, online ordering, and payment gateway integration.
* **Edge Cases handled**: 
    * Material unavailability in online systems.
    * Delivery of incorrect packages.
    * Failed deliveries (recipient not found).
* **Deliverables**: 
    * **Activity Diagram**: A procedural flow representing the online ordering process, utilizing parallel flows and decision nodes for exception handling.

---

## Modeling Techniques

The following UML diagrams were utilized to document the requirements:

1.  **Use Case Diagrams**: Used to identify the boundaries of the system and the relationship between users and specific functionalities.
2.  **Activity Diagrams**: Used to model the dynamic aspects of the system, specifically the operational workflows and logic of the hospital's supply chain.
3.  **Use Case Descriptions**: Formal documentation of preconditions, postconditions, and the "Happy Path" (main flow) versus alternative flows.
