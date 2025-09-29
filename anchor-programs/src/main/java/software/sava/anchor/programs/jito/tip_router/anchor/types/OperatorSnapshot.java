package software.sava.anchor.programs.jito.tip_router.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;

public record OperatorSnapshot(PublicKey _address,
                               Discriminator discriminator,
                               PublicKey operator,
                               PublicKey ncn,
                               long ncnEpoch,
                               int bump,
                               long slotCreated,
                               long slotFinalized,
                               boolean isActive,
                               long ncnOperatorIndex,
                               long operatorIndex,
                               int operatorFeeBps,
                               long vaultOperatorDelegationCount,
                               long vaultOperatorDelegationsRegistered,
                               long validOperatorVaultDelegations,
                               StakeWeights stakeWeights,
                               byte[] reserved,
                               VaultOperatorStakeWeight[] vaultOperatorStakeWeight) implements Borsh {

  public static final int BYTES = 14428;
  public static final int RESERVED_LEN = 256;
  public static final int VAULT_OPERATOR_STAKE_WEIGHT_LEN = 64;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int OPERATOR_OFFSET = 8;
  public static final int NCN_OFFSET = 40;
  public static final int NCN_EPOCH_OFFSET = 72;
  public static final int BUMP_OFFSET = 80;
  public static final int SLOT_CREATED_OFFSET = 81;
  public static final int SLOT_FINALIZED_OFFSET = 89;
  public static final int IS_ACTIVE_OFFSET = 97;
  public static final int NCN_OPERATOR_INDEX_OFFSET = 98;
  public static final int OPERATOR_INDEX_OFFSET = 106;
  public static final int OPERATOR_FEE_BPS_OFFSET = 114;
  public static final int VAULT_OPERATOR_DELEGATION_COUNT_OFFSET = 116;
  public static final int VAULT_OPERATOR_DELEGATIONS_REGISTERED_OFFSET = 124;
  public static final int VALID_OPERATOR_VAULT_DELEGATIONS_OFFSET = 132;
  public static final int STAKE_WEIGHTS_OFFSET = 140;
  public static final int RESERVED_OFFSET = 284;
  public static final int VAULT_OPERATOR_STAKE_WEIGHT_OFFSET = 540;

  public static Filter createOperatorFilter(final PublicKey operator) {
    return Filter.createMemCompFilter(OPERATOR_OFFSET, operator);
  }

  public static Filter createNcnFilter(final PublicKey ncn) {
    return Filter.createMemCompFilter(NCN_OFFSET, ncn);
  }

  public static Filter createNcnEpochFilter(final long ncnEpoch) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, ncnEpoch);
    return Filter.createMemCompFilter(NCN_EPOCH_OFFSET, _data);
  }

  public static Filter createBumpFilter(final int bump) {
    return Filter.createMemCompFilter(BUMP_OFFSET, new byte[]{(byte) bump});
  }

  public static Filter createSlotCreatedFilter(final long slotCreated) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, slotCreated);
    return Filter.createMemCompFilter(SLOT_CREATED_OFFSET, _data);
  }

  public static Filter createSlotFinalizedFilter(final long slotFinalized) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, slotFinalized);
    return Filter.createMemCompFilter(SLOT_FINALIZED_OFFSET, _data);
  }

  public static Filter createIsActiveFilter(final boolean isActive) {
    return Filter.createMemCompFilter(IS_ACTIVE_OFFSET, new byte[]{(byte) (isActive ? 1 : 0)});
  }

  public static Filter createNcnOperatorIndexFilter(final long ncnOperatorIndex) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, ncnOperatorIndex);
    return Filter.createMemCompFilter(NCN_OPERATOR_INDEX_OFFSET, _data);
  }

  public static Filter createOperatorIndexFilter(final long operatorIndex) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, operatorIndex);
    return Filter.createMemCompFilter(OPERATOR_INDEX_OFFSET, _data);
  }

  public static Filter createOperatorFeeBpsFilter(final int operatorFeeBps) {
    final byte[] _data = new byte[2];
    putInt16LE(_data, 0, operatorFeeBps);
    return Filter.createMemCompFilter(OPERATOR_FEE_BPS_OFFSET, _data);
  }

  public static Filter createVaultOperatorDelegationCountFilter(final long vaultOperatorDelegationCount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, vaultOperatorDelegationCount);
    return Filter.createMemCompFilter(VAULT_OPERATOR_DELEGATION_COUNT_OFFSET, _data);
  }

  public static Filter createVaultOperatorDelegationsRegisteredFilter(final long vaultOperatorDelegationsRegistered) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, vaultOperatorDelegationsRegistered);
    return Filter.createMemCompFilter(VAULT_OPERATOR_DELEGATIONS_REGISTERED_OFFSET, _data);
  }

  public static Filter createValidOperatorVaultDelegationsFilter(final long validOperatorVaultDelegations) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, validOperatorVaultDelegations);
    return Filter.createMemCompFilter(VALID_OPERATOR_VAULT_DELEGATIONS_OFFSET, _data);
  }

  public static OperatorSnapshot read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static OperatorSnapshot read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static OperatorSnapshot read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], OperatorSnapshot> FACTORY = OperatorSnapshot::read;

  public static OperatorSnapshot read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var operator = readPubKey(_data, i);
    i += 32;
    final var ncn = readPubKey(_data, i);
    i += 32;
    final var ncnEpoch = getInt64LE(_data, i);
    i += 8;
    final var bump = _data[i] & 0xFF;
    ++i;
    final var slotCreated = getInt64LE(_data, i);
    i += 8;
    final var slotFinalized = getInt64LE(_data, i);
    i += 8;
    final var isActive = _data[i] == 1;
    ++i;
    final var ncnOperatorIndex = getInt64LE(_data, i);
    i += 8;
    final var operatorIndex = getInt64LE(_data, i);
    i += 8;
    final var operatorFeeBps = getInt16LE(_data, i);
    i += 2;
    final var vaultOperatorDelegationCount = getInt64LE(_data, i);
    i += 8;
    final var vaultOperatorDelegationsRegistered = getInt64LE(_data, i);
    i += 8;
    final var validOperatorVaultDelegations = getInt64LE(_data, i);
    i += 8;
    final var stakeWeights = StakeWeights.read(_data, i);
    i += Borsh.len(stakeWeights);
    final var reserved = new byte[256];
    i += Borsh.readArray(reserved, _data, i);
    final var vaultOperatorStakeWeight = new VaultOperatorStakeWeight[64];
    Borsh.readArray(vaultOperatorStakeWeight, VaultOperatorStakeWeight::read, _data, i);
    return new OperatorSnapshot(_address,
                                discriminator,
                                operator,
                                ncn,
                                ncnEpoch,
                                bump,
                                slotCreated,
                                slotFinalized,
                                isActive,
                                ncnOperatorIndex,
                                operatorIndex,
                                operatorFeeBps,
                                vaultOperatorDelegationCount,
                                vaultOperatorDelegationsRegistered,
                                validOperatorVaultDelegations,
                                stakeWeights,
                                reserved,
                                vaultOperatorStakeWeight);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    operator.write(_data, i);
    i += 32;
    ncn.write(_data, i);
    i += 32;
    putInt64LE(_data, i, ncnEpoch);
    i += 8;
    _data[i] = (byte) bump;
    ++i;
    putInt64LE(_data, i, slotCreated);
    i += 8;
    putInt64LE(_data, i, slotFinalized);
    i += 8;
    _data[i] = (byte) (isActive ? 1 : 0);
    ++i;
    putInt64LE(_data, i, ncnOperatorIndex);
    i += 8;
    putInt64LE(_data, i, operatorIndex);
    i += 8;
    putInt16LE(_data, i, operatorFeeBps);
    i += 2;
    putInt64LE(_data, i, vaultOperatorDelegationCount);
    i += 8;
    putInt64LE(_data, i, vaultOperatorDelegationsRegistered);
    i += 8;
    putInt64LE(_data, i, validOperatorVaultDelegations);
    i += 8;
    i += Borsh.write(stakeWeights, _data, i);
    i += Borsh.writeArrayChecked(reserved, 256, _data, i);
    i += Borsh.writeArrayChecked(vaultOperatorStakeWeight, 64, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
