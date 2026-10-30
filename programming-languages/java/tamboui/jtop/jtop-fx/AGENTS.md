
# Agent Workspace Rules: Modern JavaFX Development

This document defines the constraints, architecture, and coding standards for all code generation, refactoring, and UI design within this JavaFX project.

## 0. Research
* Do not look outside of this directory.

## 1. Stack and Environment Constraints
* **UI Paradigms:** Strict MVVM (Model-View-ViewModel) or MVP pattern. Avoid writing business or data-binding logic inside Controllers.

## 2. Core Architecture Rules

### Declarative UI (FXML) vs. Programmatic UI
* Prefer **FXML** for structural, static layouts.
* Use **Programmatic Java** layouts only for highly dynamic, custom-drawn components or responsive design loops that performance-test poorly in FXML.
* Keep FXML controllers lightweight; they should only handle event routing and View-Model bindings.

### Threading & Concurrency
* **UI Thread Safety:** Never block the JavaFX Application Thread. Any long-running task, I/O, or network call *must* run on a background thread.
* **UI Updates:** Wrap background thread UI modifications strictly in `Platform.runLater()`.
* **Task API:** Use `javafx.concurrent.Task` or `javafx.concurrent.Service` for managed background operations instead of raw `java.lang.Thread`.



