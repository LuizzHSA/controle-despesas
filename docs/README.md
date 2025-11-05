# 💰 Sistema de Controle de Despesas 

**Descrição:**  
Sistema de controle de despesas desenvolvido em **Java (POO)** com persistência em arquivos `.txt`, criptografia de senhas e estrutura modular.  
Esta versão adiciona a **organização de tarefas e roadmap técnico** (PoC e MVP).

---

## 📦 Estrutura do Projeto

📂 projeto-despesas/
│
├── src/ # Código-fonte principal
│ ├── Main.java
│ ├── models/
│ ├── service/
│ └── util/
│
├── data/ # Armazenamento de dados persistentes
│ ├── despesas.txt
│ ├── tipos_despesa.txt
│ └── usuarios.txt
│
├── docs/ # Documentação técnica
│ ├── README.md
│ ├── CHANGELOG.md
│ └── roadmap.txt
│
└── tasks/ # Organização do projeto
├── kanban.txt
├── done.txt
└── priorities.json

## ⚙️ Funcionalidades Implementadas

### 🏠 Menu Principal
Entrar Despesa
Anotar Pagamento
Listar Despesas em Aberto no Período
Listar Despesas Pagas no Período
Gerenciar Tipos de Despesa
Gerenciar Usuários
Sair


### 💼 Despesas
- Inserção de despesas com descrição, valor, data e categoria  
- Armazenamento em `data/despesas.txt`  
- Subclasses de despesas (`Transporte`, `Alimentacao`, etc.)

### 💳 Pagamentos
- Registro de pagamento com data e valor  
- Atualização automática de status da despesa  

### 👥 Usuários
- Cadastro, edição e listagem  
- Criptografia MD5 aplicada nas senhas  
- Armazenamento em `data/usuarios.txt`

### ⚙️ Tipos de Despesa
- CRUD completo  
- Persistência em `data/tipos_despesa.txt`

---

## 🧱 Pilares de POO
| Conceito | Implementação |
|-----------|----------------|
| **Abstração** | Classe `Despesa` é abstrata |
| **Herança** | `Transporte` e `Alimentacao` herdam de `Despesa` |
| **Polimorfismo** | Interface `Pagavel` define contrato comum |
| **Sobrecarga/Sobrescrita** | Construtores e métodos em subclasses |
| **Atributos Estáticos** | Contagem global de despesas |
| **Encapsulamento** | Getters e setters em todas as classes |

---

## 🧩 Planejamento (Fase 2 - PoC e MVP)

📂 Diretório: [`/tasks`](../tasks)  
Contém os arquivos de controle de progresso:

| Arquivo | Descrição |
|----------|------------|
| `kanban.txt` | Tarefas organizadas por status |
| `backlog.txt` | Ideias e pendências futuras |
| `done.txt` | Histórico de entregas concluídas |
| `priorities.json` | Definição de prioridades (Alta, Média, Baixa) |

📂 Roadmap Técnico: [`/docs/roadmap.txt`](../docs/roadmap.txt)  
Contém o planejamento de cada **fase (PoC, MVP, Refinamento)** com sprints técnicas e prazos definidos.

---

🔒 Criptografia
As senhas são armazenadas criptografadas via MD5, garantindo segurança básica.
Implementação: util/CriptografiaUtils.java

📈 Versões
0.0.1	Estrutura base e menu principal
0.0.2	Estrutura de tarefas (PoC/MVP) e roadmap técnico
