LOAD DATA INFILE 'medicines.csv'
INTO TABLE medicines
CHARACTER SET euckr
FIELDS TERMINATED BY ',' 
LINES TERMINATED BY '\n'
IGNORE 1 ROWS
(@id, @name, @companyName, @imageUrl, @markFront, @markBack, @formulation, @shape, @color, @codeName, @SB)
SET med_id = @id,
med_name = @name,
med_company_name = @companyName,
med_image_url = @imageUrl,
med_mark_front = @markFront,
med_mark_back = @markBack,
med_formulation = @formulation,
med_shape = @shape,
med_color = @color,
med_code_name = @codeName,
med_sb = @SB;