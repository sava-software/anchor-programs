package software.sava.anchor.programs.drift.anchor.types;

import java.math.BigInteger;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record InsuranceFund(PublicKey vault,
                            BigInteger totalShares,
                            BigInteger userShares,
                            BigInteger sharesBase,
                            long unstakingPeriod,
                            long lastRevenueSettleTs,
                            long revenueSettlePeriod,
                            int totalFactor,
                            int userFactor) implements Borsh {

  public static final int BYTES = 112;

  public static InsuranceFund read(final byte[] _data, final int offset) {
    int i = offset;
    final var vault = readPubKey(_data, i);
    i += 32;
    final var totalShares = getInt128LE(_data, i);
    i += 16;
    final var userShares = getInt128LE(_data, i);
    i += 16;
    final var sharesBase = getInt128LE(_data, i);
    i += 16;
    final var unstakingPeriod = getInt64LE(_data, i);
    i += 8;
    final var lastRevenueSettleTs = getInt64LE(_data, i);
    i += 8;
    final var revenueSettlePeriod = getInt64LE(_data, i);
    i += 8;
    final var totalFactor = getInt32LE(_data, i);
    i += 4;
    final var userFactor = getInt32LE(_data, i);
    return new InsuranceFund(vault,
                             totalShares,
                             userShares,
                             sharesBase,
                             unstakingPeriod,
                             lastRevenueSettleTs,
                             revenueSettlePeriod,
                             totalFactor,
                             userFactor);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    vault.write(_data, i);
    i += 32;
    putInt128LE(_data, i, totalShares);
    i += 16;
    putInt128LE(_data, i, userShares);
    i += 16;
    putInt128LE(_data, i, sharesBase);
    i += 16;
    putInt64LE(_data, i, unstakingPeriod);
    i += 8;
    putInt64LE(_data, i, lastRevenueSettleTs);
    i += 8;
    putInt64LE(_data, i, revenueSettlePeriod);
    i += 8;
    putInt32LE(_data, i, totalFactor);
    i += 4;
    putInt32LE(_data, i, userFactor);
    i += 4;
    return i - offset;
  }

  @Override
  public int l() {
    return 32
         + 16
         + 16
         + 16
         + 8
         + 8
         + 8
         + 4
         + 4;
  }
}
