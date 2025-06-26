package software.sava.anchor.programs.switchboard.on_demand.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record OracleQuoteRotateEvent(PublicKey oracle) implements Borsh {

  public static final int BYTES = 32;

  public static OracleQuoteRotateEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var oracle = readPubKey(_data, offset);
    return new OracleQuoteRotateEvent(oracle);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    oracle.write(_data, i);
    i += 32;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
