package software.sava.anchor.programs.marinade;

import software.sava.anchor.programs.marinade.anchor.types.ValidatorRecord;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.lookup.AccountIndexLookupTableEntry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

public record MarinadeValidatorList(PublicKey address,
                                    List<ValidatorRecord> validators,
                                    AccountIndexLookupTableEntry[] reverseLookupTable) {

  public int validatorIndex(final PublicKey validator) {
    return AccountIndexLookupTableEntry.lookupAccountIndex(reverseLookupTable, validator);
  }

  public ValidatorRecord validatorRecord(final int index) {
    return validators.get(index);
  }

  public ValidatorRecord validatorRecord(final PublicKey validator) {
    final int index = validatorIndex(validator);
    return index < 0 ? null : validators.get(index);
  }

  public static final BiFunction<PublicKey, byte[], MarinadeValidatorList> FACTORY = (publicKey, data) -> {
    final var validators = new ArrayList<ValidatorRecord>(4_096);
    final int to = data.length - (8 + ValidatorRecord.BYTES);
    for (int offset = 0; offset <= to; ) {
      offset += 8;
      final var validatorRecord = ValidatorRecord.read(data, offset);
      if (validatorRecord.validatorAccount().equals(PublicKey.NONE)) {
        break;
      } else {
        offset += ValidatorRecord.BYTES;
        validators.add(validatorRecord);
      }
    }
    final var reverseLookupTable = new AccountIndexLookupTableEntry[validators.size()];
    int i = 0;
    for (final var validatorRecord : validators) {
      reverseLookupTable[i] = new AccountIndexLookupTableEntry(validatorRecord.validatorAccount().toByteArray(), i);
      ++i;
    }
    Arrays.sort(reverseLookupTable);
    return new MarinadeValidatorList(publicKey, validators, reverseLookupTable);
  };
}
