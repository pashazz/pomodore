create table users (
  user_id integer primary key,
  work_phase_length integer not null,
  relax_phase_length integer not null
);

create table tasks (
  ID uuid primary key default  gen_random_uuid(),
  time integer not null,
  chat_id integer not null references users(user_id),
  phase text,
  status text
)
