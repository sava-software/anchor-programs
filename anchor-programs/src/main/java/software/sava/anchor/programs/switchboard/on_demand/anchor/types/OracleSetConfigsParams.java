package software.sava.anchor.programs.switchboard.on_demand.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record OracleSetConfigsParams(PublicKey newAuthority, byte[] newSecpAuthority) implements Borsh {

  public static OracleSetConfigsParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var newAuthority = _data[i++] == 0 ? null : readPubKey(_data, i);
    if (newAuthority != null) {
      i += 32;
    }
    final var newSecpAuthority = _data[i++] == 0 ? null : new byte[64];
    if (newSecpAuthority != null) {
      Borsh.readArray(newSecpAuthority, _data, i);
    }
    return new OracleSetConfigsParams(newAuthority, newSecpAuthority);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeOptional(newAuthority, _data, i);
    if (newSecpAuthority == null || newSecpAuthority.length == 0) {
      _data[i++] = 0;
    } else {
      _data[i++] = 1;
      i += Borsh.writeArray(newSecpAuthority, _data, i);
    }
    return i - offset;
  }

  @Override
  public int l() {
    return (newAuthority == null ? 1 : (1 + 32)) + (newSecpAuthority == null || newSecpAuthority.length == 0 ? 1 : (1 + Borsh.lenArray(newSecpAuthority)));
  }
}
