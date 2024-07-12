package med.voll.api.domain.direccion;

import jakarta.validation.constraints.NotBlank;

public record DatosDireccion(
        @NotBlank
        String calle,
        Integer numero,
        @NotBlank
        String ciudad,
        @NotBlank
        String provincia
        ) {
}
