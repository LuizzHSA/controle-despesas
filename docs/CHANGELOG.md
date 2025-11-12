# 🧾 CHANGELOG — Sistema de Controle de Despesas

---

## 🏷️ Versão 0.0.2 — Estrutura de Planejamento (PoC e MVP)

🗓️ Data: 2025-11-04  
👤 Autor: Luiz Lopes de Sá

### ✨ Novidades

- Criada pasta `/tasks/` com arquivos de controle de progresso:
  - `kanban.txt` → tarefas organizadas por status
  - `backlog.txt` → ideias e pendências futuras
  - `done.txt` → histórico de tarefas concluídas
  - `priorities.json` → definição de prioridades
- Criado `/docs/roadmap.txt` com o planejamento técnico por sprint.
- Atualizado `README.md` com referência ao roadmap e tarefas.

### 🎯 Objetivo da Versão

Dar início à **Fase 2 (PoC e MVP)** do sistema, com uma estrutura organizada para acompanhar o desenvolvimento.

---

## 🏷️ Versão 0.0.1 — Estrutura Base e Menu Principal

👤 Autor: Luiz Lopes de Sá

### 🚀 Implementações

- Estrutura completa do projeto criada (`models`, `service`, `util`, `data`).
- Menu principal funcional com `println` em cada opção.
- CRUD inicial de Despesas, Usuários e Tipos.
- Persistência simples em arquivos `.txt`.
- Criptografia MD5 de senhas implementada.
- README completo e documentação inicial.

---
## v1.0.0 - MVP

- MVP concluído e publicado no repositório de produção (`main`).
- Documentação central em `docs/README.md` e `docs/roadmap.txt` atualizada.
- Estrutura de tarefas criada e registrada em `tasks/kanban.txt` e `tasks/done.txt`.
- Base do domínio com classes: `Despesa`, `TipoDespesa`, `Usuario`, `Alimentacao`, `Transporte`, `Eventual`, `Pagavel`.
- Utilitários: `CriptografiaUtil`, `ArquivoUtil`, `SistemaController`, `Main` para execução.
- Dados de exemplo em `data/usuarios.txt`, `data/despesas.txt`, `data/tipos.txt`.

Tags:
- `v1.0.0` (release principal do MVP)
- `mvp` (marcador de marco)

Commits relacionados:
- `04e6f94` squash raiz com toda a estrutura consolidada
- `639a0dc` commit atual baseado no squash
