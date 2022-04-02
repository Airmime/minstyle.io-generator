package io.minstyle.msgenerator.model;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import org.springframework.lang.NonNull;
import javax.validation.constraints.Size;

/**
 * Object represent a custom CSS file.
 *
 * @author Rémi Marion
 * @version 0.0.1
 */
@Document("custom_css")
@Data
public class CustomCSSModel {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomCSSModel.class);

	@Id
	private String id;
	@NonNull
	private LocalDateTime created;
	@NonNull
	@Size(min=6, max=6)
	private String primaryColor;
	@NonNull
	@Size(min=6, max=6)
	private String secondaryColor;
	@NonNull
	@Size(min=6, max=6)
	private String actionColor;
	@NonNull
	@Size(min=6, max=6)
	private String action2Color;
	@JsonIgnore
	private String hash;

	public CustomCSSModel(@NonNull LocalDateTime created, @NonNull String primaryColor, @NonNull String secondaryColor, @NonNull String actionColor, @NonNull String action2Color) {
		this.created = created;
		this.primaryColor = primaryColor;
		this.secondaryColor = secondaryColor;
		this.actionColor = actionColor;
		this.action2Color = action2Color;
		this.hash = generateHash();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CustomCSSModel that = (CustomCSSModel) o;
		return created.equals(that.created) && primaryColor.equals(that.primaryColor) && secondaryColor.equals(that.secondaryColor) && actionColor.equals(that.actionColor) && action2Color.equals(that.action2Color);
	}

	@Override
	public int hashCode() {
		return Objects.hash(created, primaryColor, secondaryColor, actionColor, action2Color);
	}

	/**
	 * Generate hash from primaryColor, secondaryColor, actionColor, action2Color, with SHA-256 algorithm.
	 * @return hash code.
	 */
	public String generateHash() {
		String hash = this.getPrimaryColor() + this.getSecondaryColor() + this.getActionColor() + this.getAction2Color();
		MessageDigest msdDigest = null;
		try {
			msdDigest = MessageDigest.getInstance("SHA-1");

		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("Error generating hash.", e);
			e.printStackTrace();
		}

		assert msdDigest != null;
		msdDigest.update(hash.getBytes(StandardCharsets.UTF_8), 0, hash.length());

		return hash;
	}
}
