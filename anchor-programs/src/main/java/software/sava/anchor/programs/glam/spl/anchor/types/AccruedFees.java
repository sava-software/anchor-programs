package software.sava.anchor.programs.glam.spl.anchor.types;

import java.math.BigInteger;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;

public record AccruedFees(BigInteger vaultSubscriptionFee,
                          BigInteger vaultRedemptionFee,
                          BigInteger managerSubscriptionFee,
                          BigInteger managerRedemptionFee,
                          BigInteger managementFee,
                          BigInteger performanceFee,
                          BigInteger protocolBaseFee,
                          BigInteger protocolFlowFee) implements Borsh {

  public static final int BYTES = 128;

  public static AccruedFees read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var vaultSubscriptionFee = getInt128LE(_data, i);
    i += 16;
    final var vaultRedemptionFee = getInt128LE(_data, i);
    i += 16;
    final var managerSubscriptionFee = getInt128LE(_data, i);
    i += 16;
    final var managerRedemptionFee = getInt128LE(_data, i);
    i += 16;
    final var managementFee = getInt128LE(_data, i);
    i += 16;
    final var performanceFee = getInt128LE(_data, i);
    i += 16;
    final var protocolBaseFee = getInt128LE(_data, i);
    i += 16;
    final var protocolFlowFee = getInt128LE(_data, i);
    return new AccruedFees(vaultSubscriptionFee,
                           vaultRedemptionFee,
                           managerSubscriptionFee,
                           managerRedemptionFee,
                           managementFee,
                           performanceFee,
                           protocolBaseFee,
                           protocolFlowFee);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt128LE(_data, i, vaultSubscriptionFee);
    i += 16;
    putInt128LE(_data, i, vaultRedemptionFee);
    i += 16;
    putInt128LE(_data, i, managerSubscriptionFee);
    i += 16;
    putInt128LE(_data, i, managerRedemptionFee);
    i += 16;
    putInt128LE(_data, i, managementFee);
    i += 16;
    putInt128LE(_data, i, performanceFee);
    i += 16;
    putInt128LE(_data, i, protocolBaseFee);
    i += 16;
    putInt128LE(_data, i, protocolFlowFee);
    i += 16;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
