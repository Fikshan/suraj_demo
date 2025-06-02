**BGP Automation Testing Framework**

# 📘 govTech_BGP: Business Grants Portal Automation Suite
---

## 📖 About This Project

The **govTech_BGP** repository contains **automated test scripts** for the **Business Grants Portal** (BGP), developed using:

- **Java + Selenium WebDriver**: For test automation
- **Maven**: As the build and dependency management tool
- **TestNG**: As the test framework for structuring and running tests
- **Page Object Model (POM)**: For maintainable and scalable test code
- **Eclipse IDE**: For development and execution

This project ensures **end-to-end test coverage** of BGP functionalities like:
- Eligibility checks
- Contact details validation
- Proposal submission
- Business impact input
- Cost section verification
- Form submission with validation

---

## 📂 Project Structure

govTech_BGP/
├── src/
│ └── test/
│ └── java/
│ ├── generic/
│ │     ├── BaseTest.java
│ │     └── Utility.java
│ ├── page/
│ │     ├── ContactDetailsErrorPage.java
│ │     ├── EligibilityPage.java
│ │     ├── ErrorPage.java
│ │     ├── FormActions.java
│ │     ├── HomePage.java
│ │     ├── ManualLoginPage.java
│ │     └── SignInPage.java
│ └── script/
│     ├── EndToEndContactDetailsErrorValidationTest.java
│     ├── EndToEndErrorValidationTest.java
│     ├── EndToEndFormTest.java
│     ├── EndToEndWarningValidationTest.java
│     ├── ValidLoginTest.java
│     └── InValidLoginTest.java
├── pom.xml
├── config.properties
├── testng.xml
└── README.md

---

## 🧩 Key Features
✨ Clean and modular **Page Object Model (POM)** design  
✨ Cross-browser support (optional)  
✨ Selenium Grid support for distributed test execution  
✨ Reusable utilities for wait conditions and validations  
✨ Complete end-to-end automation of BGP workflow  
✨ Robust error message and warning validation  

---

## ⚙️ Technology Stack
- **Java**
- **Selenium WebDriver**
- **TestNG**
- **Maven**
- **Selenium Grid (Optional)**

---

## 📝 Key Test Classes
- 📘 **ValidLoginTest.java**: Tests valid login functionality.
- 📕 **InValidLoginTest.java**: Tests negative login scenarios.
- 📗 **EndToEndFormTest.java**: Automates the complete BGP form flow including Eligibility, Contact Details, Proposal, Business Impact, Cost, Review, Submission and Succes Message.
- 📙 **EndToEndContactDetailsErrorValidationTest.java**: Validates error messages within the **Contact Details** section.
- 📒 **EndToEndErrorValidationTest.java**: Validates total number of error indicators if the mandatory fields remain blank across the form.
- 📓 **EndToEndWarningValidationTest.java**: Validates warning messages in the eligibilty section if selected as No option and also check on clicking of FAQ link new tab gets opened.

🧩 Key Features
✨ Clean and modular Page Object Model (POM) design
✨ Cross-browser support (optional)
✨ Reusable utilities for wait conditions and validations
✨ Complete end-to-end automation of BGP workflow
✨ Clear error message validation
