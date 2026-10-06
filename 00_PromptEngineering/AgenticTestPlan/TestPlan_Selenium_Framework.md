# Test Plan — Selenium Java Automation Framework

**Test Plan ID:** TP-SEL-001  
**Version:** 1.0  
**Date:** 2025-07-14  
**Prepared by:** QA Automation Engineer  
**Status:** Draft — Pending Review and Approval

---

## 1. Test Plan ID and Title

| Field | Value |
|---|---|
| Test Plan ID | TP-SEL-001 |
| Title | Selenium Java Automation Framework — Validation Test Plan |
| Application | Salesforce CRM Login |
| Module | Authentication (Email/Password Login) |
| Framework Stack | Selenium 4 + Java 17 + Maven + TestNG |

---

## 2. Objective and References

### Objective

Validate that the Selenium Java automation framework correctly exercises the Salesforce CRM login feature across two defined scenarios — a valid credential login and an invalid credential login — using a Page Object Model structure, PageFactory initialization, XPath locators, and `WebDriverWait`-based synchronization.

This plan covers **framework validation only**. It does not constitute end-to-end CRM regression coverage.

### References

| ID | Reference |
|---|---|
| REQ-01 | An active account with valid credentials can sign in successfully |
| REQ-02 | An incorrect password does not authenticate the user and no session is created |
| ARCH-01 | Page Object Model with PageFactory (`@FindBy`, constructor init) |
| ARCH-02 | One page object: `LoginPage.java` |
| ARCH-03 | Two test scripts: `ValidLoginTest.java`, `InvalidLoginTest.java` |
| ARCH-04 | Maven project with TestNG runner and `testng.xml` suite |
| ARCH-05 | Credentials supplied exclusively through environment variables |

---

## 3. In Scope and Out of Scope

### In Scope

- Valid login: authentication succeeds with provisioned test credentials
- Invalid login: authentication is denied with an incorrect password and no session is created
- Framework structure: Maven project layout, page object, test scripts, `pom.xml`, `testng.xml`, `BaseTest.java`
- Synchronization: `WebDriverWait` for element readiness
- Build verification: `mvn clean test` execution against the target environment

### Out of Scope

- Remember Me behavior
- Password reset and account recovery
- Multi-factor authentication (MFA) and SSO flows
- Account registration and profile management
- Performance, load, and penetration testing
- Cross-browser matrix testing beyond the agreed primary browser
- Salesforce CRM features beyond the login page

---

## 4. Requirements and Planned Coverage

| Requirement ID | Requirement | Test Scenario | Scenario Type | Planned Test Script |
|---|---|---|---|---|
| REQ-01 | Active account with valid credentials can sign in | Valid login with provisioned credentials | Positive functional | `ValidLoginTest.java` |
| REQ-02 | Incorrect password does not authenticate the user | Login attempt with wrong password | Negative functional | `InvalidLoginTest.java` |

**Coverage note:** Two scenarios are planned corresponding to the two in-scope requirements. Coverage beyond REQ-01 and REQ-02 (e.g., empty fields, account lockout, MFA) is explicitly out of scope for this plan.

---

## 5. Test Approach, Levels, and Types

### Approach

- **Automation-first:** All scenarios are automated using Selenium 4 WebDriver with Java 17.
- **Page Object Model:** UI interactions are encapsulated in `LoginPage.java` using `PageFactory.initElements`. Test scripts invoke page actions and assert outcomes; they do not contain raw locator strings.
- **Explicit synchronization:** `WebDriverWait` with `ExpectedConditions` is used for all element interactions. `Thread.sleep()` is prohibited.
- **Isolated tests:** Each `@Test` method is self-contained. `@BeforeTest` configures the TestNG `<test>` level. `@BeforeMethod` / `@AfterMethod(alwaysRun = true)` manage per-test setup and teardown.
- **No hardcoded secrets:** Credentials are injected at runtime through environment variables.

### Test Levels

| Level | Included | Notes |
|---|---|---|
| Unit | No | Framework-level unit testing is out of scope |
| Integration (API) | No | Out of scope |
| System / UI functional | Yes | Login automation via Selenium WebDriver |
| Regression | Partial | REQ-01 and REQ-02 form a baseline regression suite |
| Non-functional | No | Performance and security testing out of scope |

### Test Types

| Type | Included | Coverage |
|---|---|---|
| Positive functional | Yes | REQ-01 valid login |
| Negative functional | Yes | REQ-02 invalid password |
| Boundary value | No | Out of scope |
| Security / injection | No | Out of scope |
| Cross-browser | Proposed | Primary browser TBD; others out of scope |

---

## 6. Environment, Tools, Access, and Test Data

### Environment

| Item | Value |
|---|---|
| Application URL | `https://login.salesforce.com/?locale=in` |
| Test environment | Salesforce Developer Org or approved sandbox |
| Operating system | Not provided — to be confirmed before execution |
| Browser | Not provided — to be confirmed before execution |
| Java version | 17 (LTS) |
| Selenium version | 4.x (exact version to be agreed) |
| Maven version | 3.9.x or later |
| TestNG version | 7.x (exact version to be agreed) |

### Tools

| Tool | Purpose |
|---|---|
| Maven | Build, dependency management, test execution |
| TestNG | Test runner and reporting |
| Selenium WebDriver 4 | Browser automation |
| WebDriverManager (proposed) | Automatic browser driver resolution |
| IDE | IntelliJ IDEA or Eclipse (developer choice) |
| CI system | Not provided — identify before pipeline integration |

### Access and Prerequisites

| Prerequisite | Status |
|---|---|
| Provisioned test account (valid credentials) | **Not provided** — required before REQ-01 can execute |
| Environment variable names (`SF_USERNAME`, `SF_PASSWORD`) | Proposed — pending agreement |
| Authenticated-state locator (post-login element) | **Not provided** — required for valid login assertion |
| Authentication-failure locator / error message | **Not provided** — required for invalid login assertion |
| Browser driver access | Resolved by WebDriverManager (proposed) |

### Test Data

| Data Item | Value | Notes |
|---|---|---|
| Valid username | `$SF_USERNAME` (env var) | Provisioned account required |
| Valid password | `$SF_PASSWORD` (env var) | Provisioned account required |
| Invalid password | Synthetic (e.g., `WrongPass!999`) | No real credentials used |

---

## 7. Entry and Exit Criteria

### Entry Criteria

- [ ] Maven project compiles without errors (`mvn compile`)
- [ ] `pom.xml` declares all required dependencies with agreed versions
- [ ] Provisioned test account credentials are available as environment variables
- [ ] Authenticated-state and error-message locators are confirmed against the live DOM
- [ ] Target browser and operating system are agreed and available
- [ ] `testng.xml` is configured and the suite runs without configuration errors

### Exit Criteria

- [ ] Both `ValidLoginTest` and `InvalidLoginTest` pass in the target environment
- [ ] `mvn clean test` exits with `BUILD SUCCESS`
- [ ] TestNG report shows 2 tests run, 0 failures, 0 skips
- [ ] No hardcoded credentials appear in any source file
- [ ] All open questions in Section 10 are resolved or explicitly deferred

### Suspension Criteria

Testing is suspended when:
- The target environment is unavailable or login page is inaccessible
- Provisioned test credentials are revoked or expired
- A blocker defect prevents execution of both scenarios

### Resumption Criteria

Testing resumes when the blocking condition is resolved and entry criteria are re-confirmed.

---

## 8. Roles, Responsibilities, Estimates, and Schedule

| Role | Responsibility | Assigned To |
|---|---|---|
| QA Automation Engineer | Framework development, test script authoring, execution | Not provided |
| Test Lead / QA Lead | Plan review, approval, defect triage | Not provided |
| Developer / DevOps | Environment provisioning, CI pipeline setup | Not provided |
| Product Owner | Requirement clarification, acceptance sign-off | Not provided |

### Effort Estimates (Proposed)

| Activity | Estimated Effort |
|---|---|
| Framework scaffolding (Maven, POM, BaseTest) | 0.5 days |
| `LoginPage.java` page object | 0.5 days |
| `ValidLoginTest.java` | 0.5 days |
| `InvalidLoginTest.java` | 0.5 days |
| Environment setup and prerequisite verification | 0.5 days |
| Test execution, defect reporting, and sign-off | 0.5 days |
| **Total (proposed)** | **3 days** |

**Note:** Estimates are proposals and require agreement with the project team. Unresolved prerequisites (locators, test accounts) may extend the schedule.

---

## 9. Defect Management and Reporting

### Defect Reporting

- Defects are raised in the project's issue tracker (tool not provided — to be confirmed).
- Each defect must include: environment, browser, Java/Selenium version, reproduction steps, expected result, actual result, and supporting evidence (logs, screenshots).
- Severity and priority are proposed by the reporter and confirmed by the test lead.

### Defect Triage

- Blocker and Critical defects suspend execution until resolved.
- Major and Minor defects are triaged within one business day.

### Reporting Cadence

| Report | Frequency | Audience |
|---|---|---|
| Test execution summary | After each full run | QA Lead, Product Owner |
| Defect status update | Daily during active testing | Test Lead, Developer |
| Final test completion report | End of test cycle | All stakeholders |

---

## 10. Risks, Dependencies, Assumptions, and Open Questions

### Risks

| Risk | Likelihood | Impact | Mitigation |
|---|---|---|---|
| Salesforce DOM locators differ from supplied example XPaths | Medium | High | Verify locators against live DOM before code generation |
| Provisioned test account unavailable | Medium | High | Request account from environment owner before execution |
| Authenticated-state locator unknown | High | High | Confirm with developer or product owner before assertion authoring |
| Salesforce introduces MFA/SSO that blocks automation | Medium | High | Confirm login flow with environment owner |
| Browser driver compatibility issue | Low | Medium | Use WebDriverManager for automatic resolution |

### Dependencies

- Provisioned Salesforce test account with stable credentials
- Confirmed DOM locators for login inputs, submit button, authenticated state, and error message
- Agreed environment variables names for credential injection
- Agreed Selenium, TestNG, and Maven versions
- CI system identification (if pipeline integration is required)

### Assumptions

- The login page at `https://login.salesforce.com/?locale=in` is accessible in the test environment.
- The login flow does not require MFA or SSO for the provisioned test account.
- WebDriverManager is approved for managing browser driver resolution.
- Synthetic invalid password data (`WrongPass!999`) does not match any real account.
- Framework scope is limited to exactly one page object and two test scripts.

### Open Questions

| # | Question | Raised By | Status |
|---|---|---|---|
| OQ-01 | What is the confirmed XPath or selector for the authenticated-state element after valid login? | QA Team | Open |
| OQ-02 | What is the exact error message and its DOM locator shown on invalid login? | QA Team | Open |
| OQ-03 | What browser and OS are confirmed for execution? | QA Team | Open |
| OQ-04 | What Selenium 4.x and TestNG 7.x patch versions are approved? | QA Team | Open |
| OQ-05 | What are the agreed environment variable names for credentials? | QA Team | Open |
| OQ-06 | Is a CI pipeline required as part of this deliverable? | QA Team | Open |
| OQ-07 | What issue tracker is used for defect reporting? | QA Team | Open |

---

## 11. Suspension and Resumption Criteria

_(Documented in Section 7 — Entry and Exit Criteria above.)_

---

## 12. Test Deliverables and Approval

### Deliverables

| Deliverable | Description | Status |
|---|---|---|
| `TestPlan_Selenium_Framework.md` | This test plan document | Draft |
| `pom.xml` | Maven build and dependency configuration | Not created |
| `testng.xml` | TestNG suite configuration | Not created |
| `BaseTest.java` | WebDriver lifecycle and configuration | Not created |
| `LoginPage.java` | Page object for the Salesforce login page | Not created |
| `ValidLoginTest.java` | Test script for REQ-01 valid login | Not created |
| `InvalidLoginTest.java` | Test script for REQ-02 invalid login | Not created |

### Approval

| Approver | Role | Signature / Date |
|---|---|---|
| Not provided | Test Lead | Pending |
| Not provided | Product Owner | Pending |
| Not provided | Development Lead | Pending |

---

## Final Review Checklist

- [x] Test Plan ID and title are defined
- [x] Objectives and references are documented
- [x] In scope and out of scope are clearly separated
- [x] Requirements are mapped to planned coverage
- [x] Test approach, levels, and types are defined
- [x] Environment, tools, access, and test data are specified
- [x] Entry and exit criteria are measurable
- [x] Roles, estimates, and schedule are documented (as proposals)
- [x] Defect management and reporting cadence are defined
- [x] Risks, dependencies, assumptions, and open questions are listed
- [x] Suspension and resumption criteria are documented
- [x] Deliverables and approval signatories are listed
- [x] Unavailable inputs are marked **Not provided** (not left blank)
- [x] Generated work is clearly distinguished from executed work
- [x] No fabricated test results, credentials, or DOM locators are included

---

*This test plan was created using the Ricepot QA Generic Template — Profile B (Test Plan).  
All thresholds, estimates, ownership, and open questions are proposals requiring agreement before execution.*
