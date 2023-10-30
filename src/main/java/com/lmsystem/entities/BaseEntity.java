package com.lmsystem.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.Instant;


@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Integer id;
	@Column(name="created_at", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Instant createdAt;
	@Column(name="modified_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Instant modifiedAt;


	@PrePersist
	private void setCreatedAt() {
		var date=Instant.now();
		this.createdAt=date;
		this.modifiedAt=date;
	}
	
	@PreUpdate
	private void setModifiedAt() {
		this.modifiedAt = Instant.now();
	}
	
	
	
}
