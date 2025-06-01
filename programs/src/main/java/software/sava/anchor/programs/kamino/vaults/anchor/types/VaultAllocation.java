package software.sava.anchor.programs.kamino.vaults.anchor.types;

import java.math.BigInteger;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record VaultAllocation(PublicKey reserve,
                              PublicKey ctokenVault,
                              long targetAllocationWeight,
                              // Maximum token invested in this reserve
                              long tokenAllocationCap,
                              long ctokenVaultBump,
                              long[] configPadding,
                              long ctokenAllocation,
                              long lastInvestSlot,
                              BigInteger tokenTargetAllocationSf,
                              long[] statePadding) implements Borsh {

  public static final int BYTES = 2160;

  public static VaultAllocation read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var reserve = readPubKey(_data, i);
    i += 32;
    final var ctokenVault = readPubKey(_data, i);
    i += 32;
    final var targetAllocationWeight = getInt64LE(_data, i);
    i += 8;
    final var tokenAllocationCap = getInt64LE(_data, i);
    i += 8;
    final var ctokenVaultBump = getInt64LE(_data, i);
    i += 8;
    final var configPadding = new long[127];
    i += Borsh.readArray(configPadding, _data, i);
    final var ctokenAllocation = getInt64LE(_data, i);
    i += 8;
    final var lastInvestSlot = getInt64LE(_data, i);
    i += 8;
    final var tokenTargetAllocationSf = getInt128LE(_data, i);
    i += 16;
    final var statePadding = new long[128];
    Borsh.readArray(statePadding, _data, i);
    return new VaultAllocation(reserve,
                               ctokenVault,
                               targetAllocationWeight,
                               tokenAllocationCap,
                               ctokenVaultBump,
                               configPadding,
                               ctokenAllocation,
                               lastInvestSlot,
                               tokenTargetAllocationSf,
                               statePadding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    reserve.write(_data, i);
    i += 32;
    ctokenVault.write(_data, i);
    i += 32;
    putInt64LE(_data, i, targetAllocationWeight);
    i += 8;
    putInt64LE(_data, i, tokenAllocationCap);
    i += 8;
    putInt64LE(_data, i, ctokenVaultBump);
    i += 8;
    i += Borsh.writeArray(configPadding, _data, i);
    putInt64LE(_data, i, ctokenAllocation);
    i += 8;
    putInt64LE(_data, i, lastInvestSlot);
    i += 8;
    putInt128LE(_data, i, tokenTargetAllocationSf);
    i += 16;
    i += Borsh.writeArray(statePadding, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
