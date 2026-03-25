# Homework 3: Requirements Analysis & System Design

This repository contains the **Requirements Analysis Document** developed for **Homework 3** of the **Modern Methods of Software Engineering** course (September 2024). This project marks the transition from "what" the system should do (Requirements Elicitation) to "how" the system is structured internally (Analysis and Design).

## Project Overview

Building directly on the use cases and scenarios defined in Homework 2, this assignment focuses on the structural and dynamic modeling of the system using the **Entity-Boundary-Control (EBC)** pattern and advanced UML diagrams.

### Key Components of the Analysis
1.  **Object Identification (EBC Pattern)**:
    * **Entity Objects**: Identification of persistent data structures (e.g., Database records, User profiles).
    * **Boundary Objects**: Definition of the interfaces between the system and the actors (e.g., User interfaces, API gateways).
    * **Control Objects**: Modeling the "glue" or logic that coordinates the behavior between boundaries and entities.
2.  **Dynamic Modeling (Sequence Diagrams)**: Mapping the major use cases from Homework 2 to the identified EBC objects to visualize the chronological flow of messages and logic.
3.  **Structural Modeling (Class Diagrams)**: Developing a comprehensive UML Class Diagram that defines the attributes, methods, and relationships (aggregation, composition, inheritance) between system objects.
4.  **State Modeling (Statechart Diagrams)**: Visualizing the lifecycle of key system objects, documenting how they transition between different states in response to events.

---

## Methodologies & Patterns

This project applies industry-standard software engineering patterns to ensure system scalability and maintainability:

* **Separation of Concerns**: Using the Entity-Boundary-Control pattern to decouple the user interface from the business logic and data storage.
* **Behavioral Realization**: Transforming textual use cases into formal Sequence Diagrams to identify missing logic or required object interactions.
* **Object-Oriented Analysis (OOA)**: Refining the domain model into a technical class structure ready for implementation.
