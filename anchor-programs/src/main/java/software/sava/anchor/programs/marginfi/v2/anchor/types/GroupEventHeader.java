package software.sava.anchor.programs.marginfi.v2.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record GroupEventHeader(PublicKey signer, PublicKey marginfiGroup) implements Borsh {

  public static GroupEventHeader read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var signer = _data[i++] == 0 ? null : readPubKey(_data, i);
    if (signer != null) {
      i += 32;
    }
    final var marginfiGroup = readPubKey(_data, i);
    return new GroupEventHeader(signer, marginfiGroup);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeOptional(signer, _data, i);
    marginfiGroup.write(_data, i);
    i += 32;
    return i - offset;
  }

  @Override
  public int l() {
    return (signer == null ? 1 : (1 + 32)) + 32;
  }
}
