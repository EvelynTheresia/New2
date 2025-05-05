# BTO Management System

This is a CLI-based Build-To-Order (BTO) housing application system built using Java, designed for use by HDB Managers, Officers, and Applicants. The system enables users to manage housing projects, apply for flats, handle officer registrations, respond to enquiries, and generate reports.

##  Features

###  Applicant
- View available BTO projects
- Apply for BTO flat (based on eligibility)
- Withdraw application or request withdrawal
- Submit, view, edit, or delete enquiries
- Switch password

###  HDB Officer
- Register to handle a BTO project
- View handled projects
- Book flats for successful applicants
- Handle enquiries related to assigned projects
- Generate booking receipt for applicant
- Switch to applicant dashboard

###  HDB Manager
- Create, edit, delete BTO projects
- Toggle project visibility
- View/approve officer registrations
- View/approve BTO applications
- Handle withdrawal requests
- Respond to enquiries for managed projects
- Generate booking reports based on filters

## 📁 Data Storage

The system loads and saves data from `.csv` files:
- `ApplicantList.csv`
- `OfficerList.csv`
- `ManagerList.csv`
- `ProjectList.csv`
- `ApplicationList.csv`
- `EnquiryList.csv`
- `RegistrationList.csv`

Each entity is mapped to a corresponding loader class (`ApplicationCSVLoader`, `EnquiryCSVLoader`, etc.) which handles file I/O operations.


