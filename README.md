# Thindesk, Dashboard Administrativo de Chamados

Dashboard administrativo integrado ao sistema de chamados Thindesk. O painel consome uma API REST que disponibiliza indicadores, gráficos, ranking e filtros sobre os chamados armazenados no MongoDB Atlas, permitindo ao responsável acompanhar o andamento do atendimento e apoiar a tomada de decisões.

O projeto parte do sistema Thindesk disponibilizado na disciplina (Spring Boot, Thymeleaf, Spring Security e MongoDB), acrescentando a API e a tela do dashboard.

## Entrega

* [Documentação (PDF)](documento_e_video/documentacao.pdf)
* [Vídeo de apresentação](documento_e_video/video-apresentacao.mp4)

## Funcionalidades do dashboard

* Indicadores gerais: total de chamados, abertos, em andamento, concluídos e tempo médio de atendimento
* Gráfico de chamados atendidos por período
* Gráfico da situação dos chamados
* Ranking dos tipos de chamado mais atendidos
* Tabela com os chamados e as observações técnicas
* Filtros por período de abertura, status, tipo e técnico
* Atualização pelo botão Atualizar e automática a cada minuto

A página do dashboard carrega os dados chamando a API por JavaScript.

## O que foi acrescentado ao Thindesk

* Campos `dataAbertura`, `dataConclusao` e `observacaoTecnica` na entidade `Chamado`, necessários para a análise por período, o tempo médio e as considerações técnicas
* Validação dos campos obrigatórios (descrição, status e tipo) e tratamento dos dados recebidos
* Novos endpoints de consulta de chamados e os endpoints do dashboard
* Tela do dashboard e item "Dashboard" no menu lateral
* Carga inicial de chamados de exemplo quando a coleção está vazia
* Conexão com o banco e senha do administrador fora do código, em um arquivo `.env`

## API

Todos os endpoints de consulta aceitam os filtros opcionais `inicio` e `fim` (data de abertura, formato `AAAA-MM-DD`), `status`, `tipo` e `tecnico`.

| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/api/chamados` | Lista os chamados |
| GET | `/api/chamados/{id}` | Busca um chamado |
| POST | `/api/chamados` | Cadastra um chamado |
| PUT | `/api/chamados/{id}` | Atualiza um chamado |
| GET | `/api/dashboard/indicadores` | Indicadores gerais |
| GET | `/api/dashboard/atendidos-por-periodo` | Chamados concluídos por data |
| GET | `/api/dashboard/status` | Quantidade de chamados por status |
| GET | `/api/dashboard/tipos` | Ranking dos tipos de chamado |

Exemplo de corpo para cadastro:

```json
{
  "descricao": "Monitor sem imagem",
  "status": "Aberto",
  "tipo": "Manutenção",
  "tecnico": "João",
  "usuario": "Carla"
}
```

Possíveis erros:

| Código | Quando acontece |
|---|---|
| 400 | Campo obrigatório vazio, JSON inválido ou data fora do formato |
| 401 | Credenciais inválidas |
| 404 | Chamado não encontrado |

Fora do navegador (por exemplo, no Postman), a API aceita autenticação Basic com o usuário `admin` e a senha definida no `.env`.

## Pré-requisitos

* Java 17 ou superior
* Conta gratuita no [MongoDB Atlas](https://www.mongodb.com/products/platform)
* Git

Não é necessário instalar o Maven, o projeto inclui o Maven Wrapper (`mvnw`).

## Configuração

1. No MongoDB Atlas, crie um cluster gratuito, um usuário de banco e libere o seu IP em **Network Access**.
2. Copie a string de conexão em **Connect > Drivers**.
3. Faça uma cópia do arquivo `.env.example` com o nome `.env` e preencha:

```
MONGODB_URI=mongodb+srv://USUARIO:SENHA@SEU_CLUSTER.mongodb.net/?retryWrites=true&w=majority
ADMIN_SENHA=uma-senha-forte
```

O arquivo `.env` não é enviado ao GitHub.

## Como executar

No Windows:

```
mvnw.cmd spring-boot:run
```

No Linux ou macOS:

```
./mvnw spring-boot:run
```

Acesse http://localhost:8080, entre com o usuário `admin` e a senha do `.env`, e clique em **Dashboard** no menu lateral.

## Banco de dados

O sistema usa o banco `thindesk` no MongoDB Atlas. Os chamados ficam na coleção `chamados`. Os indicadores não são gravados, eles são calculados pela API a cada consulta.

## Fluxo de branches

O projeto segue o Gitflow: `main` para as versões entregues, `develop` para a integração e branches `feature/*` para cada funcionalidade. A implementação da API está na branch `feature/api-dashboard`.
