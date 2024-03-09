INSERT INTO `users` (`first_name`, `last_name`, `user_name`, `phone_number`, `password`, `role`, `status`, `created_at`, `updated_at`, `store_id`)
VALUES
    ('Manohar', 'Krishna', 'mano', '1234567890', '$2a$10$afL0cY5uaUt7j0MwDSCQEeIjD7jBk6qGCPGPyhI2mTkBMKrgTxBv6', 'ADMIN', 'ACTIVE', now(), now(), 1),
    ('Manoranjani', 'Yalamati', 'mano.y', '1234567890', '$2a$12$bApcDtnDoah7YoNYxzO2Uek79XPX8kW.mCI8ZRA9fbWjHUou0isA6', 'SUPER_ADMIN', 'ACTIVE', now(), now(), 1);