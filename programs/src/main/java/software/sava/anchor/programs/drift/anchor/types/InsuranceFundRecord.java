package software.sava.anchor.programs.drift.anchor.types;

import java.math.BigInteger;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record InsuranceFundRecord(long ts,
                                  int spotMarketIndex,
                                  int perpMarketIndex,
                                  int userIfFactor,
                                  int totalIfFactor,
                                  long vaultAmountBefore,
                                  long insuranceVaultAmountBefore,
                                  BigInteger totalIfSharesBefore,
                                  BigInteger totalIfSharesAfter,
                                  long amount) implements Borsh {

  public static final int BYTES = 76;

  public static InsuranceFundRecord read(final byte[] _data, final int offset) {
    int i = offset;
    final var ts = getInt64LE(_data, i);
    i += 8;
    final var spotMarketIndex = getInt16LE(_data, i);
    i += 2;
    final var perpMarketIndex = getInt16LE(_data, i);
    i += 2;
    final var userIfFactor = getInt32LE(_data, i);
    i += 4;
    final var totalIfFactor = getInt32LE(_data, i);
    i += 4;
    final var vaultAmountBefore = getInt64LE(_data, i);
    i += 8;
    final var insuranceVaultAmountBefore = getInt64LE(_data, i);
    i += 8;
    final var totalIfSharesBefore = getInt128LE(_data, i);
    i += 16;
    final var totalIfSharesAfter = getInt128LE(_data, i);
    i += 16;
    final var amount = getInt64LE(_data, i);
    return new InsuranceFundRecord(ts,
                                   spotMarketIndex,
                                   perpMarketIndex,
                                   userIfFactor,
                                   totalIfFactor,
                                   vaultAmountBefore,
                                   insuranceVaultAmountBefore,
                                   totalIfSharesBefore,
                                   totalIfSharesAfter,
                                   amount);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, ts);
    i += 8;
    putInt16LE(_data, i, spotMarketIndex);
    i += 2;
    putInt16LE(_data, i, perpMarketIndex);
    i += 2;
    putInt32LE(_data, i, userIfFactor);
    i += 4;
    putInt32LE(_data, i, totalIfFactor);
    i += 4;
    putInt64LE(_data, i, vaultAmountBefore);
    i += 8;
    putInt64LE(_data, i, insuranceVaultAmountBefore);
    i += 8;
    putInt128LE(_data, i, totalIfSharesBefore);
    i += 16;
    putInt128LE(_data, i, totalIfSharesAfter);
    i += 16;
    putInt64LE(_data, i, amount);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
