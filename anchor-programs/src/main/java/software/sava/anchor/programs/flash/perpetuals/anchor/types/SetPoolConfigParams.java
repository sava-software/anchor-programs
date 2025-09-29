package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record SetPoolConfigParams(Permissions permissions,
                                  PublicKey oracleAuthority,
                                  long maxAumUsd,
                                  long stakingFeeShareBps,
                                  int vpVolumeFactor,
                                  long[] stakingFeeBoostBps,
                                  long minLpPriceUsd,
                                  long maxLpPriceUsd) implements Borsh {

  public static final int BYTES = 126;
  public static final int STAKING_FEE_BOOST_BPS_LEN = 6;

  public static SetPoolConfigParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var permissions = Permissions.read(_data, i);
    i += Borsh.len(permissions);
    final var oracleAuthority = readPubKey(_data, i);
    i += 32;
    final var maxAumUsd = getInt64LE(_data, i);
    i += 8;
    final var stakingFeeShareBps = getInt64LE(_data, i);
    i += 8;
    final var vpVolumeFactor = _data[i] & 0xFF;
    ++i;
    final var stakingFeeBoostBps = new long[6];
    i += Borsh.readArray(stakingFeeBoostBps, _data, i);
    final var minLpPriceUsd = getInt64LE(_data, i);
    i += 8;
    final var maxLpPriceUsd = getInt64LE(_data, i);
    return new SetPoolConfigParams(permissions,
                                   oracleAuthority,
                                   maxAumUsd,
                                   stakingFeeShareBps,
                                   vpVolumeFactor,
                                   stakingFeeBoostBps,
                                   minLpPriceUsd,
                                   maxLpPriceUsd);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(permissions, _data, i);
    oracleAuthority.write(_data, i);
    i += 32;
    putInt64LE(_data, i, maxAumUsd);
    i += 8;
    putInt64LE(_data, i, stakingFeeShareBps);
    i += 8;
    _data[i] = (byte) vpVolumeFactor;
    ++i;
    i += Borsh.writeArrayChecked(stakingFeeBoostBps, 6, _data, i);
    putInt64LE(_data, i, minLpPriceUsd);
    i += 8;
    putInt64LE(_data, i, maxLpPriceUsd);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
