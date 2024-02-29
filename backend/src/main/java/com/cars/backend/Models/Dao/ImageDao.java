package com.cars.backend.Models.Dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ImageDao {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Lob
	@Column(name = "image_data", columnDefinition = "LONGBLOB", nullable = false)
	byte [] image;

	public Long getId() {
		return id;
	}

	@NonNull
	public byte[] getImage() {
		return image;
	}

	public ImageDao setImage(@NonNull byte[] image) {
		this.image = image;
		return this;
	}

}
