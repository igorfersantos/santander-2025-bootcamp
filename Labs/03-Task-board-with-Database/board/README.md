# Board Lab

Lab to create a Jira like CLI-Board App using MySQL 
with liquibase for migrations.

TODOS:
- [ ] Document
- [ ] Upgrade dependencies
- [ ] Refactor where necessary
- [ ] Come back here to create tests after the Test module
- [ ] Save the UML Schema to the parent folder
- [ ] Do the optional requisites at [original-readme.md](original-readme.md)

Challenges:
- [ ] Improve CardDAO.findById() to bring only cards from the selected board
- [ ] Improve code functions with similar functionalities to use a common or Utils function
  - Example: CardService.java functions 
- [ ] Add a history table of blocks and unblocks of the card (optional requisite)
  - Example: Project Warehouse - Audit Table