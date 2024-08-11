package software.sava.anchor.programs.marinade.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record DepositStakeAccountEvent(PublicKey state,
                                       PublicKey stake,
                                       long delegated,
                                       PublicKey withdrawer,
                                       int stakeIndex,
                                       PublicKey validator,
                                       int validatorIndex,
                                       long validatorActiveBalance,
                                       long totalActiveBalance,
                                       long userMsolBalance,
                                       long msolMinted,
                                       long totalVirtualStakedLamports,
                                       long msolSupply) implements Borsh {

  public static final int BYTES = 192;

  public static DepositStakeAccountEvent read(final byte[] _data, final int offset) {
    int i = offset;
    final var state = readPubKey(_data, i);
    i += 32;
    final var stake = readPubKey(_data, i);
    i += 32;
    final var delegated = getInt64LE(_data, i);
    i += 8;
    final var withdrawer = readPubKey(_data, i);
    i += 32;
    final var stakeIndex = getInt32LE(_data, i);
    i += 4;
    final var validator = readPubKey(_data, i);
    i += 32;
    final var validatorIndex = getInt32LE(_data, i);
    i += 4;
    final var validatorActiveBalance = getInt64LE(_data, i);
    i += 8;
    final var totalActiveBalance = getInt64LE(_data, i);
    i += 8;
    final var userMsolBalance = getInt64LE(_data, i);
    i += 8;
    final var msolMinted = getInt64LE(_data, i);
    i += 8;
    final var totalVirtualStakedLamports = getInt64LE(_data, i);
    i += 8;
    final var msolSupply = getInt64LE(_data, i);
    return new DepositStakeAccountEvent(state,
                                        stake,
                                        delegated,
                                        withdrawer,
                                        stakeIndex,
                                        validator,
                                        validatorIndex,
                                        validatorActiveBalance,
                                        totalActiveBalance,
                                        userMsolBalance,
                                        msolMinted,
                                        totalVirtualStakedLamports,
                                        msolSupply);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    state.write(_data, i);
    i += 32;
    stake.write(_data, i);
    i += 32;
    putInt64LE(_data, i, delegated);
    i += 8;
    withdrawer.write(_data, i);
    i += 32;
    putInt32LE(_data, i, stakeIndex);
    i += 4;
    validator.write(_data, i);
    i += 32;
    putInt32LE(_data, i, validatorIndex);
    i += 4;
    putInt64LE(_data, i, validatorActiveBalance);
    i += 8;
    putInt64LE(_data, i, totalActiveBalance);
    i += 8;
    putInt64LE(_data, i, userMsolBalance);
    i += 8;
    putInt64LE(_data, i, msolMinted);
    i += 8;
    putInt64LE(_data, i, totalVirtualStakedLamports);
    i += 8;
    putInt64LE(_data, i, msolSupply);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return 32
         + 32
         + 8
         + 32
         + 4
         + 32
         + 4
         + 8
         + 8
         + 8
         + 8
         + 8
         + 8;
  }
}
