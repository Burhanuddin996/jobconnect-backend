# JobConnect — Job Portal REST API

A complete Spring Boot backend for a Naukri/LinkedIn-style job portal:
candidates, recruiters, companies, jobs, applications, search/filter,
pagination, validation, centralized error handling, and JWT-based
authentication with role-based authorization.

This project is already fully coded. You only need to:
1. Create one empty PostgreSQL database.
2. Open the project in Eclipse.
3. Put your PostgreSQL password in one file.
4. Run it.

Hibernate (`spring.jpa.hibernate.ddl-auto=update`) then creates every
table for you automatically the first time the app starts — you never
write SQL `CREATE TABLE` statements by hand.

---

## 1. One-time setup

### a) Install/check tools
- JDK 21
- Eclipse (with Maven support — it's built in, called "m2e")
- PostgreSQL + pgAdmin
- Postman

### b) Create ONE empty database
Hibernate can create tables, but it cannot create the database itself.
Open pgAdmin (or `psql`) once and run:

```sql
CREATE DATABASE jobconnect;
```

That's the only manual database step. Everything else (all tables,
columns, foreign keys) is generated automatically by Hibernate.

### c) Set your PostgreSQL password
Open `src/main/resources/application.properties` and replace
`YOUR_PASSWORD` with your actual PostgreSQL password:

```properties
spring.datasource.password=YOUR_PASSWORD
```

Everything else in that file is already correct.

---

## 2. Import into Eclipse

1. `File → Import → Maven → Existing Maven Projects`
2. Browse to the unzipped `jobconnect` folder and select it.
3. Eclipse will read `pom.xml` and download all dependencies
   automatically (needs internet access the first time).
4. Wait for the build to finish (bottom-right progress bar).

## 3. Run it

Right-click `JobconnectApplication.java` → `Run As → Java Application`.

Console output should end with something like:
```
Tomcat started on port(s): 8080 (http)
Started JobconnectApplication in x.xxx seconds
```

Open pgAdmin → `jobconnect` database → `Schemas → public → Tables`.
You should now see: `users`, `candidate_profiles`, `skills`,
`candidate_skills`, `companies`, `jobs`, `applications` — all created
automatically. Every time you add a new field to an entity and restart
the app, Hibernate updates the table for you (that's what
`ddl-auto=update` does).

---

## 4. Testing with Postman (JSON only — no code needed)

Import `JobConnect.postman_collection.json` (included in this zip) into
Postman: `File → Import` → select the file.

The collection already contains every request with a ready-to-send
JSON body, grouped exactly like the original guide's testing plan:

1. **Auth** — Register (seeker), Register (recruiter), Login (seeker), Login (recruiter)
2. **Users** — Get all, Get by id, Update, Delete
3. **Candidate Profile** — Create, Get mine, Update, Add/Remove skill
4. **Skills** — Create, Get all
5. **Company** — Create, Get all, Get by id, Update
6. **Jobs** — Create, Get all (paginated), Get by id, Update, Delete, Search, Filter
7. **Applications** — Apply, My applications, Applicants for a job, Update status

### How the JWT flow works in Postman
1. Run **Register (seeker)** and **Register (recruiter)** once each.
2. Run **Login (seeker)** — copy the `token` field from the response.
3. On every protected request, go to the **Authorization** tab → type
   `Bearer Token` → paste the token.
4. Repeat with the recruiter's token for recruiter-only actions
   (creating a company, posting jobs, updating application status).

`/api/auth/**` and all `GET` endpoints for jobs/companies/skills are
public (no token needed) so anyone can browse listings without logging
in. Everything else (creating a profile, posting a job, applying,
updating status) requires a valid `Bearer` token.

---

## 5. Business rules already implemented

- A recruiter can only create/edit/delete jobs that belong to **their
  own** company (checked from the JWT, not from a request field).
- A candidate cannot apply to the same job twice.
- Passwords are stored BCrypt-hashed — never in plain text, never
  returned in any API response.
- Consistent success shape: `{ "status": 200, "message": "...", "data": {...} }`
- Consistent error shape: `{ "timestamp", "status", "message", "path" }`
  (and a `fieldErrors` map for validation failures).

---

## 6. Common problems (same as the original guide)

| Symptom | Fix |
|---|---|
| `PostgreSQL driver error` | Check `org.postgresql.Driver` spelling in `application.properties` |
| `Connection refused` | PostgreSQL service isn't running, or wrong port (default 5432) |
| `404 in Postman` | Check method + exact URL + that the app is actually running |
| `400 Bad Request` | Check JSON field names against the DTO, read the `fieldErrors` in the response |
| `401 Unauthorized` | Missing/expired/invalid Bearer token |
| `403 Forbidden` | Valid token, but wrong role or you don't own that company/job |
| `Table not created` | Check `spring.jpa.hibernate.ddl-auto=update` and that the DB connected at all |

---

## 7. Resume line (once you've actually run and tested it)

> **JobConnect — Job Portal REST API**
> Developed a Spring Boot backend for a Naukri-style job portal with
> candidate, recruiter, company, job and application modules.
> Implemented RESTful APIs with Spring MVC, Spring Data JPA and
> Hibernate on PostgreSQL, including search/filtering, pagination,
> DTO-based validation, centralized exception handling, and JWT
> authentication with role-based authorization.

## 8. Interview questions this project prepares you for
Why DTOs · JPA vs Hibernate · JpaRepository · @OneToMany/@ManyToOne ·
@JoinColumn · Controller-Service-Repository · global exception handling ·
JWT flow · authentication vs authorization · preventing duplicate
applications · pagination · lazy vs eager fetching · the N+1 problem.
