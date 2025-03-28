package software.sava.anchor.programs.meteora.staging.dlmm.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;

public record FeeParameterUpdate(PublicKey lbPair,
                                 int protocolShare,
                                 int baseFactor) implements Borsh {

  public static final int BYTES = 36;

  public static FeeParameterUpdate read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var lbPair = readPubKey(_data, i);
    i += 32;
    final var protocolShare = getInt16LE(_data, i);
    i += 2;
    final var baseFactor = getInt16LE(_data, i);
    return new FeeParameterUpdate(lbPair, protocolShare, baseFactor);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    lbPair.write(_data, i);
    i += 32;
    putInt16LE(_data, i, protocolShare);
    i += 2;
    putInt16LE(_data, i, baseFactor);
    i += 2;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
