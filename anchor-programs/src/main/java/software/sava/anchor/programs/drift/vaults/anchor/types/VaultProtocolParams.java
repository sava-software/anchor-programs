package software.sava.anchor.programs.drift.vaults.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record VaultProtocolParams(PublicKey protocol,
                                  long protocolFee,
                                  int protocolProfitShare) implements Borsh {

  public static final int BYTES = 44;

  public static VaultProtocolParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var protocol = readPubKey(_data, i);
    i += 32;
    final var protocolFee = getInt64LE(_data, i);
    i += 8;
    final var protocolProfitShare = getInt32LE(_data, i);
    return new VaultProtocolParams(protocol, protocolFee, protocolProfitShare);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    protocol.write(_data, i);
    i += 32;
    putInt64LE(_data, i, protocolFee);
    i += 8;
    putInt32LE(_data, i, protocolProfitShare);
    i += 4;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
