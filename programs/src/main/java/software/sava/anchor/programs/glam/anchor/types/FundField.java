package software.sava.anchor.programs.glam.anchor.types;

import java.lang.String;

import software.sava.core.borsh.Borsh;

import static java.nio.charset.StandardCharsets.UTF_8;

public record FundField(FundFieldName name, String value, byte[] _value) implements Borsh {


  public static FundField createRecord(final FundFieldName name, final String value) {
    return new FundField(name, value, value.getBytes(UTF_8));
  }

  public static FundField read(final byte[] _data, final int offset) {
    int i = offset;
    final var name = FundFieldName.read(_data, i);
    i += Borsh.len(name);
    final var value = Borsh.string(_data, i);
    return new FundField(name, value, value.getBytes(UTF_8));
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(name, _data, i);
    i += Borsh.write(_value, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.len(name) + Borsh.len(_value);
  }
}
