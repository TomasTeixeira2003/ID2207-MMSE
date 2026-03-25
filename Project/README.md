# Swedish Event Planners (SEP) - Internal Management System

This repository contains the core internal management system for **Swedish Event Planners (SEP)**, a premier event management company based in Stockholm. This project was developed as part of the **Modern Methods in Software Engineering** course (2024/2025).

## 🏢 Business Context

SEP specializes in end-to-end planning for high-profile business events, including workshops, conferences, and summer schools. The system is designed to handle the complex organizational hierarchy and workflows required to manage decorations, catering, filming, and music for multiple parallel events.

### Organizational Structure
The system supports the specific workflows of four key departments:
* **Administration:** Manages client relations, initial business meetings, and event planning requests.
* **Financial:** Oversees budget approvals, cost-benefit analysis, and final billing.
* **Production:** Coordinates specialized teams (Decorations, Photography, Catering).
* **Services:** Manages logistical support and human resource requests.

---

## Getting Started

### Prerequisites
* **Java**: Version 23
* **Maven**: Latest version installed and configured in your PATH.

### Installation & Build
1.  **Clone the repository**:
    ```bash
    git clone git@github.com:TomasTeixeira2003/ID2207-MMSE.git
    cd Project
    ```
2.  **Build the project** using Maven:
    ```bash
    mvn clean install
    ```
3.  **Run the application**:
    ```bash
    cd target
    java -jar portal-1.0.0.jar
    ```

### Accessing the Portal
Once the server is running, access the web interface at:
**[http://localhost:8080](http://localhost:8080)**

**Initial Administrator Credentials:**
* **Username:** `admin`
* **Password:** `bakalispwd`
> *Note: The Administration Manager has the elevated privileges required to create and manage other user accounts within the system.*

---

## System Functionalities

Based on the SEP business model, the application supports the following core workflows:

### 1. Event Planning & Client Management
* **Client Requests**: Automated forms for registered clients to request events, specify attendee counts, and set budget expectations.
* **Business Meetings**: Digital recording of initial client consultations by Senior Customer Service Officers.

### 2. Operational Workflows
* **Task Assignment**: Production Managers can delegate specific tasks (e.g., Decorations) to specialists, who receive them in a personalized task list.
* **Resource Management**: Production and Service Managers can trigger **Recruitment Requests** to HR for additional personnel (Graphic Designers, etc.).

### 3. Financial Oversight
* **Budget Adjustments**: Formalized flow for Production/Service Managers to request budget shifts from the Financial Manager.
* **Financial Approvals**: Interface for the Financial Department to review and approve resource expenses.
