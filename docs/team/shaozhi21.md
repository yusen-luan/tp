# Project Portfolio: Shao Zhi (@ShaoZhi21)

## Overview
I led two core features that improved daily TA workflows: a fast, non-destructive `view` command for inspecting a student's full details, and a selective, robust `attendance` mechanism for marking weekly presence. I complemented these with ongoing documentation updates and defect fixes to ensure the product's behavior matched user expectations.

### Reflections on Growth
This project was my first experience balancing multiple stakeholder needs—from understanding TA pain points to designing defensive systems that prevent user mistakes. I learned that good software isn't just about making things work; it's about making things **safe, predictable, and forgiving**. My documentation work taught me that clarity in writing directly translates to user confidence, and that anticipating confusion is as important as anticipating bugs.

## Key Feature #1 — View Command

### Understanding the Need
TAs need to quickly inspect a student's information (modules, grades, attendance, consultations, tags, remarks) without navigating away or modifying data. Prior to this feature, retrieving a complete snapshot required multiple commands and context switches, slowing grading and consultations. I recognized this as an opportunity to create a safe, read-only inspection tool that TAs could trust.

### My Contribution
I designed and implemented the `view` command from the ground up, handling both index-based (`view INDEX`) and ID-based (`view s/STUDENT_ID`) lookups. This dual approach required careful thought about how users naturally think—sometimes they're working from a list (index), other times they know exactly who they want (student ID).

**Key Implementation Decisions:**
- Built a pure read path with zero model mutations, ensuring repeated views never accidentally change data
- Designed comprehensive error handling for invalid indexes, malformed IDs, and edge cases
- Created structured output displaying all student information (personal details, attendance records, consultation schedules, grades, and remarks) in a scannable format

### Challenges and Learning
One critical insight came when handling filtered lists. Initially, I hadn't considered that users might filter by module and then try to view a student—should the index map to the filtered list or the full dataset? After discussion, I realized **indexes must always reference what users can see**, not hidden data. This taught me that good UX means matching user mental models, not just technical convenience.

Testing this feature also reinforced the importance of defensive programming. I wrote comprehensive tests covering boundary cases (negative indexes, out-of-bounds, empty fields) and verified idempotency—a term I'd only read about before but now deeply understand through practice.

### Impact
- Became the primary inspection tool for TAs conducting grading and consultations
- Reduced navigation overhead and cognitive load
- Demonstrated that read-only operations, when done right, build user trust

## Key Feature #2 — Attendance

### Understanding the Need
TAs record attendance weekly throughout the semester, but a destructive "replace-all" model would make roll-marking terrifying—one wrong command could wipe weeks of history. I needed to design a system that was **selective by nature**, where updating Week 5 could never accidentally affect Week 1 or Week 13.

### My Contribution
I implemented the `attendance` command with three interaction modes: individual by index (`attendance INDEX w/WEEK present|absent|unmark`), by student ID (`attendance s/STUDENT_ID w/WEEK present|absent|unmark`), and bulk operations (`attendance all w/WEEK present|absent|unmark`). This flexibility emerged from observing how TAs actually work—sometimes they're marking one late student, other times they're recording attendance for an entire tutorial at once.

**Key Design Choices:**
- Week-scoped updates that modify only the specified week (1-13), leaving all other weeks untouched
- Reversible operations with `unmark` to correct mistakes without leaving false data behind
- Case-insensitive status input (present/absent/unmark) to reduce typing friction during live tutorials
- Comprehensive validation that catches invalid weeks, malformed input, and nonexistent students before any data changes

### Challenges and Learning
The bulk mode (`attendance all`) presented an interesting design challenge: should it respect filtered lists or operate on all students? I initially leaned toward filtered lists for consistency with other commands, but after considering the use case—TAs marking an entire tutorial class—I realized filtering would create confusion. **The lesson: sometimes consistency takes a backseat to task alignment.** If a TA says "all," they mean all students, not "all visible students."

Implementing `unmark` also taught me about user psychology. Initially, I considered just overwriting with a blank value, but that felt wrong—unmarking is semantically different from "marking as unknown." This distinction matters for data integrity and user confidence.

### Impact
- TAs can confidently mark attendance week-by-week without fear of data loss
- Bulk operations streamline tutorial-wide attendance recording
- The `unmark` feature provides a safety net for correcting mistakes
- Selective updates proved that good design prevents errors rather than just catching them

## Continuous Documentation and Defect Fixes
Although the two features above were my main focus, I maintained product quality through ongoing documentation and bug triage:
- User Guide: Kept examples executable, clarified required fields for `add`, documented command and filter case sensitivity, and aligned multi-value field semantics (modules/tags/consultations replace; grades/attendance are selective). Noted that legacy phone/address are not shown on the card.
- Bug fixes: Closed gaps in input validation, refined error messages, and aligned edit semantics. Ensured that behavior matched the documentation across releases.

## What This Means for Users
- A fast, read-only `view` command to get all relevant student details without context switches.
- Robust, week-specific `attendance` updates (including bulk) that are simple, safe, and reversible when needed.
- Documentation that matches the product—fewer surprises, clearer expectations, and faster onboarding for new TAs.
