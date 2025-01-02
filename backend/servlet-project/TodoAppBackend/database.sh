#!/bin/bash
set -e

# PostgreSQL configuration
DB_NAME="tasklist"
DB_USER="sahithi"
DB_PASSWORD="password"
DB_DATA_DIR="/Users/sahithi/Assignment/data"

echo "Checking if PostgreSQL is running..."
if ! pg_isready -h localhost -p 5432 > /dev/null 2>&1; then
    echo "Starting PostgreSQL using pg_ctl..."
    pg_ctl -D "$DB_DATA_DIR" start
else
    echo "PostgreSQL is already running."
fi

export PGPASSWORD=$DB_PASSWORD

echo "Checking if database '$DB_NAME' exists..."
if ! psql -h localhost -U $DB_USER -d postgres -p 5432 -tc "SELECT 1 FROM pg_database WHERE datname = '$DB_NAME'" | grep -q 1; then
    echo "Creating database '$DB_NAME'..."
    psql -h localhost -U $DB_USER -d postgres -p 5432 -c "CREATE DATABASE $DB_NAME;"
else
    echo "Database '$DB_NAME' already exists."
fi

echo "Initializing database with schema and seed data..."

# Create tasks table
psql -h localhost -U $DB_USER -d $DB_NAME -p 5432 <<EOF
CREATE TABLE IF NOT EXISTS tasks (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    status VARCHAR(50) DEFAULT 'pending'
);

-- Insert seed data
INSERT INTO tasks (title, description, status) VALUES
    ('Buy groceries', 'Milk, eggs, bread, and cheese', 'pending'),
    ('Finish project', 'Complete all the sections for the software development project', 'in-progress'),
    ('Call Mom', 'Check in with Mom and catch up', 'completed');
EOF

echo "Database initialized successfully."

# Cleanup
unset PGPASSWORD
echo "Database management completed."
