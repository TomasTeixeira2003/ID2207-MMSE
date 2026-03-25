# Homework 4: System Design & Object Design

This repository contains the **System and Object Design Document** developed for **Homework 4** of the **Modern Methods of Software Engineering** course (September 2024). This final phase of the project focuses on the architectural decisions and high-level structural mapping required to transform the analysis model into a technical implementation.

## Project Overview

Building on the requirements analysis from Homework 3, this assignment addresses the architectural design, hardware/software mapping, and the application of design patterns to ensure a robust and secure system.

### Key Components of the Design
1.  **Subsystem Decomposition**: Organizing the system into smaller, manageable subsystems. This is visualized using **Class Diagrams** to show the structural relationships between the high-level components.
2.  **Hardware/Software Mapping**: Using **UML Deployment Diagrams** to map subsystems to specific processors and physical components, identifying the underlying technologies and infrastructure.
3.  **Persistent Storage Strategy**: 
    * Identification of persistent objects.
    * Detailed description of the storage management strategy (e.g., Relational Databases vs. File Systems).
4.  **Security & Control**:
    * **Access Control**: Implementation of an **Access Matrix** to define user permissions.
    * **Global Control Flow**: Defining the execution flow of the system.
    * **Boundary Conditions**: Handling system initialization, termination, and failure recovery.
5.  **Object Design & Patterns**: Application of specific **Design Patterns** (e.g., Observer, Strategy, or Factory) to solve recurring design problems and improve code reusability.
6.  **Formal Contracts (OCL)**: Writing formal constraints and contracts for noteworthy classes using **Object Constraint Language (OCL)** to ensure system integrity.

---

## Design Principles Applied

The design phase adheres to core software architecture principles:

* **Deployment Strategy**: Designing for scalability by separating software components across different nodes in the deployment diagram.
* **Access Control Matrix**: Ensuring a secure environment through a formal authorization strategy and data confidentiality.
* **Design Pattern Integration**: Leveraging industry-standard patterns to reduce coupling and increase the flexibility of the object model.
* **Formal Verification**: Using OCL to define invariants and pre/post-conditions that are not easily captured by standard class diagrams.
