# [qqb.kitchen](https://qqb.kitchen/)
**🏗️ This project is currently being developed. More details coming soon. 🚧**




## Table of Contents
- [About](#about)
- [Project Overview](#project-overview)
- [SQL Database Schema](#sql-database-schema)
- [Tech Stack](#tech-stack)
- [Latest User Interface](#latest-user-interface)
- [Features](#features)
- [Copyright](#copyright)

## About
This is my personal recipe website designed to provide users with an engaging platform for discovering and managing recipes. If you have questions about my implementation, feel free to reach out to [me](https://www.linkedin.com/in/dongningsong/).

## Project Overview
**[qqb.kitchen](https://qqb.kitchen/)** is a containerized web application featuring a <code style="color : darkorange">**Java**</code> Spring Boot backend 
 with <code style="color : blue">**PostgreSQL**</code> database and a <code style="color : fuchsia">**React**</code>/<code style="color : greenyellow">**TypeScript**</code> frontend. 
 The infrastructure leverages <code style="color : cyan">**Docker**</code> for containerization and orchestration, 
 with deployment automated through a <code style="color : green">**CI/CD**</code> pipeline that builds and pushes custom images to DockerHub, 
 then deploys them alongside PostgreSQL to <code style="color : gold">**Amazon Web Services (AWS)**</code>

## SQL Database Schema

The current database structure:

<img src="src/main/resources/image/schema.png" alt="SQL Schema" width=500/>
 

## Tech Stack
- **Frontend**: ![React](https://img.shields.io/badge/React-%2320232a.svg?logo=react&logoColor=%2361DAFB), ![TypeScript](https://img.shields.io/badge/TypeScript-3178C6?logo=typescript&logoColor=fff)
- **Backend**: ![Java](https://img.shields.io/badge/Java-%23ED8B00.svg?logo=openjdk&logoColor=blue) ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?logo=springboot&logoColor=fff) (including custom RESTful APIs development)
- **Database**: ![Postgres](https://img.shields.io/badge/Postgres-%23316192.svg?logo=postgresql&logoColor=white) (with PL/pgSQL for stored procedures and functions)
- **DevOps**:
  - ![Docker](https://img.shields.io/badge/Docker-2496ED?logo=docker&logoColor=fff) (containerization and orchestration)
  - ![AWS](https://img.shields.io/badge/AWS-%23FF9900.svg?logo=amazon-web-services&logoColor=white) (EC2, Route 53, IAM)
  - ![GitHub Actions](https://img.shields.io/badge/GitHub_Actions-2088FF?logo=github-actions&logoColor=white) (CI/CD pipelines)
  - Shell Scripting (automation)

## Latest User Interface

### Desktop View
<img src="src/main/resources/image/desktop-view/main-page.png" alt="Main Page" width=800/><br>
<img src="src/main/resources/image/desktop-view/multi-selection-1.png" alt="Multi-Selection" width=800/><br>
<img src="src/main/resources/image/desktop-view/recipe-instruction.png" alt="Recipe Instruction" width=800/>

### Mobile View
<div>
  <img src="src/main/resources/image/mobile-view/main-page.png" alt="Mobile Main Page" height="600" width="400"/>
  <img src="src/main/resources/image/mobile-view/multi-selection-1.png" alt="Mobile Multi-Selection" height="600" width="400"/>
</div>

## Features

- **Recipe Display** _(Implemented)_: Features a homepage with sections for "Today’s Recipes" and "Newest Recipes," providing users with fresh and relevant content.
- **Dynamic Navigation System** _(Implemented)_: Allows users to filter recipes using multi-select dropdown menus, with support for dietary restrictions, meal types, cuisines, and more, all managed through URL-based state management.
- **Recipe Instruction Page** _(Implemented)_: Clicking on a recipe image redirects users to a dedicated instruction page. This page displays categorized tags, a detailed ingredient list, and step-by-step preparation instructions, offering users a comprehensive cooking guide.
- **Upcoming Feature** _(Ongoing)_: Placeholder

## Copyright

© 2025 [Dongning Song](https://qqb.kitchen) - All Rights Reserved

This project is not licensed for distribution, modification, or commercial use.
See the [LICENSE](LICENSE) file for details.


