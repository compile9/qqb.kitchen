# tempNameHolder
Discover and explore personalized recipes tailored to your taste!

## Table of Contents

- [Project Overview](#project-overview)
- [Tech Stack](#tech-stack)
- [Latest User Interface](#latest-user-interface)
- [Features](#features)
- [Disclaimer](#disclaimer)

## Project Overview

**tempNameHolder** is a responsive recipe website designed to provide users with an intuitive and engaging platform for discovering and managing recipes. The application features a robust backend built with **Java Spring Boot** and a **PostgreSQL database**, utilizing **PL/pgSQL** for stored procedures and functions to ensure efficient data management. The frontend, developed with **React** and **TypeScript**, delivers a modern, interactive user experience.

The application is hosted on **Amazon Web Services (AWS)** for reliable and scalable accessibility. Ongoing development is focused on introducing new features, enhancing functionality, and improving the overall user experience.


## Tech Stack
- **Frontend**: React, TypeScript
- **Backend**: Java Spring Boot (including custom RESTful APIs development)
- **Database**: PostgreSQL (with PL/pgSQL for stored procedures and functions)
- **DevOps**: Docker, CI/CD pipelines (TBD), Amazon Web Services (AWS)

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

## Disclaimer

This project is currently under active development and is part of my personal portfolio. It is intended to showcase my skills in fullstack development. Please note:
- This project is not suitable for production use due to its experimental nature and ongoing development.
- It is intended for demonstration purposes only.
- Features are being actively added and refined.