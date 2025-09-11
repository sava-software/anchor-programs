package software.sava.anchor.programs.glam.mint;

import java.math.BigInteger;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record MintPolicy(int lockupPeriod,
                         long maxCap,
                         long minSubscription,
                         long minRedemption,
                         long reserved,
                         BigInteger allowlist,
                         PublicKey[] blocklist,
                         PublicKey[] protocolFlowFee) implements Borsh {

  public static MintPolicy read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var lockupPeriod = getInt32LE(_data, i);
    i += 4;
    final var maxCap = getInt64LE(_data, i);
    i += 8;
    final var minSubscription = getInt64LE(_data, i);
    i += 8;
    final var minRedemption = getInt64LE(_data, i);
    i += 8;
    final var reserved = getInt64LE(_data, i);
    i += 8;
    final var allowlist = getInt128LE(_data, i);
    i += 16;
    final var blocklist = _data[i++] == 0 ? null : Borsh.readPublicKeyVector(_data, i);
    if (blocklist != null) {
      i += Borsh.lenVector(blocklist);
    }
    final var protocolFlowFee = _data[i++] == 0 ? null : Borsh.readPublicKeyVector(_data, i);
    return new MintPolicy(lockupPeriod,
                          maxCap,
                          minSubscription,
                          minRedemption,
                          reserved,
                          allowlist,
                          blocklist,
                          protocolFlowFee);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt32LE(_data, i, lockupPeriod);
    i += 4;
    putInt64LE(_data, i, maxCap);
    i += 8;
    putInt64LE(_data, i, minSubscription);
    i += 8;
    putInt64LE(_data, i, minRedemption);
    i += 8;
    putInt64LE(_data, i, reserved);
    i += 8;
    putInt128LE(_data, i, allowlist);
    i += 16;
    if (blocklist == null || blocklist.length == 0) {
      _data[i++] = 0;
    } else {
      _data[i++] = 1;
      i += Borsh.writeVector(blocklist, _data, i);
    }
    if (protocolFlowFee == null || protocolFlowFee.length == 0) {
      _data[i++] = 0;
    } else {
      _data[i++] = 1;
      i += Borsh.writeVector(protocolFlowFee, _data, i);
    }
    return i - offset;
  }

  @Override
  public int l() {
    return 4
         + 8
         + 8
         + 8
         + 8
         + 16
         + (blocklist == null || blocklist.length == 0 ? 1 : (1 + Borsh.lenVector(blocklist)))
         + (protocolFlowFee == null || protocolFlowFee.length == 0 ? 1 : (1 + Borsh.lenVector(protocolFlowFee)));
  }
}
