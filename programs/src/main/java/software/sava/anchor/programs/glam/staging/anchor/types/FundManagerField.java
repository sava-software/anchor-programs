package software.sava.anchor.programs.glam.staging.anchor.types;

import java.lang.String;

import software.sava.core.borsh.Borsh;

import static java.nio.charset.StandardCharsets.UTF_8;

public record FundManagerField(FundManagerFieldName name, String value, byte[] _value) implements Borsh {

  public static FundManagerField createRecord(final FundManagerFieldName name, final String value) {
    return new FundManagerField(name, value, value.getBytes(UTF_8));
  }

  public static FundManagerField read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var name = FundManagerFieldName.read(_data, i);
    i += Borsh.len(name);
    final var value = Borsh.string(_data, i);
    return new FundManagerField(name, value, value.getBytes(UTF_8));
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
