create table if not exists clients (
    card_number numeric(20) primary key,
    points_sum int8 not null
 );
create table if not exists checks (
    id uuid primary key,
    card_number numeric(20) not null references clients,
    check_sum double precision not null
 );

CREATE INDEX checks_card_number ON checks(card_number);

create table if not exists check_positions (
    id uuid primary key,
    check_id uuid not null references checks,
    position_sum double precision not null
 );

CREATE INDEX positions_check_id ON check_positions(check_id);
