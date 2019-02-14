CREATE TYPE opportunity_stage AS ENUM ('To be discussed', 'In discussion', 'Approved', 'Declined', 'Expired');

ALTER TABLE opportunities
ALTER COLUMN stage TYPE opportunity_stage USING (stage::opportunity_stage);

