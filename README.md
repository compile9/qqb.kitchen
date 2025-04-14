# [qqb.kitchen](https://qqb.kitchen/)
This is my personal recipe website designed to provide users with an engaging platform for discovering and managing recipes.

## Table of Contents

- [Project Overview](#project-overview)
- [Tech Stack](#tech-stack)
- [Latest User Interface](#latest-user-interface)
- [Features](#features)
- [Disclaimer](#disclaimer)

## Project Overview

**[qqb.kitchen](https://qqb.kitchen/)** is a containerized web application featuring a <code style="color : darkorange">**Java**</code> Spring Boot backend 
with <code style="color : blue">**PostgreSQL**</code> database and a <code style="color : fuchsia">**React**</code>/<code style="color : greenyellow">**TypeScript**</code> frontend. 
The infrastructure leverages <code style="color : cyan">**Docker**</code> for containerization and orchestration, 
with deployment automated through a <code style="color : green">**CI/CD**</code> pipeline that builds and pushes custom images to DockerHub, 
then deploys them alongside PostgreSQL to <code style="color : gold">**Amazon Web Services (AWS)**</code>. 
A shell script executes the Docker Compose configuration to orchestrate proper container initialization sequence. This architecture enables consistent, 
reliable deployments across environments while development continues on new features and enhancements.

## Tech Stack
- <code style="color : magenta">**Frontend**</code>: React, TypeScript
- <code style="color : gold">**Backend**</code>: Java Spring Boot (including custom RESTful APIs development)
- <code style="color : cyan">**Database**</code>: PostgreSQL (with PL/pgSQL for stored procedures and functions)
- <code style="color : green">**DevOps**</code>: Docker (containerization and orchestration), AWS (EC2, Route 53, IAM), Shell Scripting (automation), GitHub Actions (CI/CD pipelines)

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

## Database Schema

The current database structure:

<img src="src/main/resources/image/schema.png" alt="SQL Schema" width=500/>

## Disclaimer

This project is part of my personal portfolio showcasing my ability to develop full stack applications and implement DevOps best practices for deployment and infrastructure management. While you're welcome to explore and learn from the code, please respect that it represents my intellectual effort and approach. Not intended for production use or direct replication for commercial purposes. Feel free to reach out if you have questions about my implementation!