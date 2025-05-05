# 🧪 Validador de CPF com Múltiplas Threads

Este projeto em Java realiza a **validação de CPFs** presentes em 30 arquivos de texto, utilizando diferentes quantidades de **threads** para avaliar o impacto do paralelismo no tempo de execução.

---

## 🎯 Objetivo

Analisar o desempenho de uma aplicação que processa grandes volumes de dados (CPFs) ao variar o número de threads utilizadas para leitura e validação, observando o tempo de execução de cada cenário.

---

## 📁 Estrutura do Projeto

validador-cpf/  
├── src/  
│   └── br/  
│       └── com/  
│           └── validadorcpf/  
│               ├── Main.java  
│               └── CPFValidator.java  
├── cpf/  # Contém os 30 arquivos de entrada com CPFs (f1-4-25.txt até f30-4-25.txt)  
├── resultados/  # Contém arquivos com o tempo de execução por versão  
└── README.md

---

## ⚙️ Como funciona

1. O programa solicita ao usuário uma versão (1 a 8), que determina o número de threads.
2. Cada thread processa uma parte dos arquivos.
3. Todos os CPFs de cada arquivo são validados segundo o algoritmo da Receita Federal.
4. O terminal exibe o status (válido ou inválido) de cada CPF.
5. Um arquivo `versao_X_threads.txt` é criado com o **tempo total de execução** da versão escolhida.

---

## 🧠 Validação de CPF

O algoritmo utilizado segue as regras oficiais para cálculo dos dígitos verificadores. A implementação está em `CPFValidator.java`.

---

## 🔢 Configuração das Versões

| Versão | Threads | Arquivos por Thread |
|--------|---------|---------------------|
| 1      | 1       | 30                  |
| 2      | 2       | 15                  |
| 3      | 3       | 10                  |
| 4      | 5       | 6                   |
| 5      | 6       | 5                   |
| 6      | 10      | 3                   |
| 7      | 15      | 2                   |
| 8      | 30      | 1                   |

---

## ⏱️ Resultados Reais de Execução

| Versão | Threads | Tempo de Execução (ms) |
|--------|---------|------------------------|
| 1      | 1       | **6454** ✅ mais rápido
| 2      | 2       | 9583
| 3      | 3       | 9834
| 4      | 5       | 10136
| 5      | 6       | 10000
| 6      | 10      | 10686
| 7      | 15      | 9939
| 8      | 30      | 10896

---

## 📊 Análise dos Resultados

> A versão com apenas 1 thread foi a mais eficiente. À medida que o número de threads aumentou, o tempo de execução também cresceu. Isso sugere que o paralelismo gerou mais **overhead** do que benefícios neste caso. Como o gargalo principal é a leitura dos arquivos (I/O), o uso de múltiplas threads **não trouxe ganho de desempenho** e, em alguns casos, **piorou**.

Esse comportamento é comum em aplicações onde o acesso ao disco é intenso e a quantidade de CPU necessária é pequena.

---

## ▶️ Como Executar

1. Abra o projeto no **IntelliJ IDEA**.
2. Execute a classe `Main.java`.
3. Quando solicitado, informe o número da versão (1 a 8).
4. Observe os CPFs sendo validados no terminal.
5. Verifique o tempo de execução salvo em `resultados/versao_X_threads.txt`.

---

## 🧰 Requisitos

- Java 17 ou superior
- IntelliJ IDEA (ou outro IDE compatível)

---

## 👤 Desenvolvido por

**Nome:** Aline Oliveira de Pinho  
**Curso:** Ciência da Computação  
**Disciplina:** Programação Concorrente e Distribuída  
**Ano:** 2025

---

