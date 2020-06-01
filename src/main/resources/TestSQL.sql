

  SELECT DISTINCT u.id, u.creation_date,
                  u.email, u.first_name, u.name,
                  u.uid, u.update_date,
                  au.authority_level as highestAuthoLevel
  FROM app_user u, authority au, app_user_authority uau
  WHERE u.id = uau.app_user_id
    AND au.id = uau.authority_id
    AND au.authority_level = (
      SELECT au2.authority_level
      FROM authority au2
      WHERE au2.id = 1
    )
  GROUP BY u.id, au.authority_level
  ORDER BY u.id ASC ;
