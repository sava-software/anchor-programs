package software.sava.anchor.programs.glam.staging.anchor.types;

import java.lang.String;

import software.sava.core.borsh.Borsh;

import static java.nio.charset.StandardCharsets.UTF_8;

public record CompanyField(CompanyFieldName name, String value, byte[] _value) implements Borsh {

  public static CompanyField createRecord(final CompanyFieldName name, final String value) {
    return new CompanyField(name, value, value.getBytes(UTF_8));
  }

  public static CompanyField read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var name = CompanyFieldName.read(_data, i);
    i += Borsh.len(name);
    final var value = Borsh.string(_data, i);
    return new CompanyField(name, value, value.getBytes(UTF_8));
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(name, _data, i);
    i += Borsh.writeVector(_value, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.len(name) + Borsh.lenVector(_value);
  }
}
